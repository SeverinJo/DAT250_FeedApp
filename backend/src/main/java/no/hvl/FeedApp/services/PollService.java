package no.hvl.FeedApp.services;


import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.PollApiContract.*;
import no.hvl.FeedApp.api.contract.PollApiContract.PollCreationRequest;
import no.hvl.FeedApp.database.entities.Poll;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.entities.Vote;
import no.hvl.FeedApp.database.entities.VoteOption;
import no.hvl.FeedApp.database.repositories.PollRepo;
import no.hvl.FeedApp.database.repositories.UserRepo;
import no.hvl.FeedApp.database.repositories.VoteRepo;
import no.hvl.FeedApp.messaging.EventPublisher;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepo polls;
    private final UserRepo users;
    private final VoteRepo voteRepo;
    private final EventPublisher events;



    @Transactional
    public Long createPoll(Authentication auth, PollCreationRequest req) {
        User creator = getUser(auth);

        Poll poll = new Poll();
        poll.setCreatedBy(creator);
        poll.setQuestion(req.question());
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plus(Duration.ofDays(7)));
        for(int i = 0; i < req.voteOptions().size(); i++) {
            poll.addVoteOption(req.voteOptions().get(i).caption());
        }

        Long id = polls.save(poll).getId();
        events.publishPollCreated(id, poll.getQuestion());
        return id;
    }


    @Cacheable(cacheNames = "pollDetail", key = "#pollId")
    @Transactional(readOnly = true)
    public PollResponse getPoll(Authentication auth, Long pollId) {
        User user = getUser(auth);
        Poll poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with pollId: " + pollId));

        //Has the user voted for this poll?
        Long myOptionId = voteRepo.findByVoterIdAndPollId(user.getId(), pollId)
                .map(v -> v.getVoteOption().getId())
                .orElse(null);


        List<VoteOptionResponse> voResponse = new ArrayList<>();
        for (int i = 0; i < poll.getOptions().size(); i++) {
            VoteOption vo = poll.getOptions().get(i);
            boolean isMyVote = myOptionId != null && myOptionId.equals(vo.getId());
            int numberOfVotes = (int) voteRepo.countByVoteOptionId(vo.getId());
            VoteOptionResponse voResp = new VoteOptionResponse(vo.getId(), vo.getPresentationOrder(), vo.getCaption(), numberOfVotes, isMyVote);
            voResponse.add(voResp);
        }

        return new PollResponse(
                poll.getId(),
                poll.getQuestion(),
                voResponse,
                poll.getCreatedBy().getUsername(),
                poll.getValidUntil() == null ? null : poll.getValidUntil().atZone(ZoneId.of("Europe/Oslo"))
       );
    }


    @Cacheable(cacheNames = "pollList", key = "#onlyMyPolls ? 'my:' + #auth.name : 'all'")
    @Transactional(readOnly = true)
    public List<PollResponse> getPolls(Authentication auth, boolean onlyMyPolls) {
        User user = getUser(auth);
        List<Poll> pollsToReturn = onlyMyPolls
                ? polls.findAllByCreatedBy_IdOrderByPublishedAt(user.getId())
                : polls.findAllByOrderByPublishedAtDescIdDesc();

        List<PollResponse> response = new ArrayList<>(pollsToReturn.size());
        for(Poll p : pollsToReturn) {
            response.add(getPoll(auth, p.getId()));
        }

        return response;
    }


    @CacheEvict(cacheNames = {"pollDetail", "pollList"}, allEntries = false, key = "#pollId")
    @Transactional
    public void vote(Authentication auth, Long pollId, Long voteOptionId) {
         User user = getUser(auth);

         if(pollId == null || voteOptionId == null)
             throw new IllegalArgumentException("Missing input values");

         Poll poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with id: " + pollId));

         //Is poll valid?
        if(poll.getValidUntil() != null && Instant.now().isAfter(poll.getValidUntil())) {
            throw new IllegalStateException("Poll is closed for voting!");
        }

        //Does VoteOption belong to Poll
        VoteOption vo = poll.getOptions()
                .stream()
                .filter(option -> option.getId().equals(voteOptionId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("VoteOption: " +voteOptionId+" does not belong to given poll: " + pollId));

        //Find existing vote given by this user
        var maybeVoted = voteRepo.findByVoterIdAndPollId(user.getId(), pollId);
        if(maybeVoted.isPresent()) {
            //User has voted
            var userVote = maybeVoted.get();
            if(userVote.getVoteOption().getId().equals(voteOptionId)){
                return; //Do nothing
            } else {
                userVote.setVoteOption(vo);
                userVote.setVotedAt(Instant.now());
                voteRepo.save(userVote);
                events.publishVote(pollId, voteOptionId, user.getId(), true);
            }
        } else {
            //User has not voted on this poll before
            Vote vote = new Vote();
            vote.setVoter(user);
            vote.setPoll(poll);
            vote.setVoteOption(vo);
            vote.setVotedAt(Instant.now());
            voteRepo.save(vote);
            events.publishVote(pollId, voteOptionId, user.getId(), false);
        }
    }


    //----------------- HELPERS ---------------------
    private User getUser(Authentication auth) {
        String uname = auth.getName();
        return users.findByUsername(uname)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }


    //----------------- Broker methods (needs to avoid authentication) ---------------------
    public void voteAnonymous(Long pollId, Long voteOptionId, Instant when) {
        var poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with id: " + pollId));
        if(poll.getValidUntil() != null && Instant.now().isAfter(poll.getValidUntil()))
            throw new IllegalStateException("Poll is closed, no voting allowed!");

        var vo = poll.getOptions().stream().filter(o -> o.getId().equals(voteOptionId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Option not in poll!"));

        var v = new Vote();
        v.setVoter(null);
        v.setPoll(poll);
        v.setVoteOption(vo);
        v.setVotedAt(when != null ? when : Instant.now());
        voteRepo.save(v);

        events.publishVote(pollId, voteOptionId, null, false);
    }


    public void voteFromBroker(Long userId, Long pollId, Long voteOptionId, Instant when) {
        if(userId == null) {
            voteAnonymous(pollId, voteOptionId, when);
        }

        Poll poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with id: " + pollId));
        if(poll.getValidUntil() != null && Instant.now().isAfter(poll.getValidUntil()))
            throw new IllegalStateException("Poll is closed, no voting allowed!");

        VoteOption vo = poll.getOptions().stream().filter(o -> o.getId().equals(voteOptionId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No option with id: " + voteOptionId + " in polls"));

        var maybeVote = voteRepo.findByVoterIdAndPollId(userId, pollId);
        if(maybeVote.isPresent()) {
            var vote = maybeVote.get();
            if(!vote.getVoteOption().getId().equals(voteOptionId)){
                vote.setVoteOption(vo);
                vote.setVotedAt(when != null ? when : Instant.now());
                voteRepo.save(vote);
                events.publishVote(pollId, voteOptionId, userId, true);
            } else {
                //Do nothing
            }
        } else {

            User u = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id: " + userId));
            Vote v = new Vote();
            v.setVoter(u);
            v.setPoll(poll);
            v.setVoteOption(vo);
            v.setVotedAt(when != null ? when : Instant.now());
            voteRepo.save(v);
            events.publishVote(pollId, voteOptionId, userId, false);
        }
    }
}
