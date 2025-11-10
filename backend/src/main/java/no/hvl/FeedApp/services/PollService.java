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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

       return polls.save(poll).getId();

    }


    @Cacheable(cacheNames = "pollStaticDetail", key = "#pollId")
    @Transactional(readOnly = true)
    public PollStaticData getPollStatic(Long pollId) {
        Poll poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with pollId: " + pollId));

        List<VoteOptionStatic> opts = poll.getOptions().stream()
                .map(vo -> new VoteOptionStatic(
                        vo.getId(),
                        vo.getPresentationOrder(),
                        vo.getCaption(),
                        (int) voteRepo.countByVoteOptionId(vo.getId())
                ))
                .toList();

        return new PollStaticData(
                poll.getId(),
                poll.getQuestion(),
                opts,
                poll.getCreatedBy().getUsername(),
                poll.getValidUntil()
        );
    }


    @Transactional(readOnly = true)
    public PollResponse getPoll(Authentication auth, Long pollId) {
        User user = getUser(auth);

        PollStaticData stat = getPollStatic(pollId);

        Long myOptionId = voteRepo.findByVoterIdAndPollId(user.getId(), pollId)
                .map(v -> v.getVoteOption().getId())
                .orElse(null);

        List<VoteOptionResponse> voResponse = stat.options().stream()
                .map(voStat -> new VoteOptionResponse(
                        voStat.id(),
                        voStat.presentationOrder(),
                        voStat.caption(),
                        voStat.numberOfVotes(),
                        myOptionId != null && myOptionId.equals(voStat.id())
                ))
                .toList();

        return new PollResponse(
                stat.id(),
                stat.question(),
                voResponse,
                stat.createdBy(),
                stat.validUntil() == null ? null : stat.validUntil().atZone(ZoneId.of("Europe/Oslo"))
        );
    }


//    @Cacheable(cacheNames = "pollDetail", key = "#pollId")
//    @Transactional(readOnly = true)
//    public PollResponse getPoll(Authentication auth, Long pollId) {
//        User user = getUser(auth);
//        Poll poll = polls.findById(pollId).orElseThrow(() -> new IllegalArgumentException("No poll with pollId: " + pollId));
//
//        //Has the user voted for this poll?
//        Long myOptionId = voteRepo.findByVoterIdAndPollId(user.getId(), pollId)
//                .map(v -> v.getVoteOption().getId())
//                .orElse(null);
//
//
//        List<VoteOptionResponse> voResponse = new ArrayList<>();
//        for (int i = 0; i < poll.getOptions().size(); i++) {
//            VoteOption vo = poll.getOptions().get(i);
//            boolean isMyVote = myOptionId != null && myOptionId.equals(vo.getId());
//            int numberOfVotes = (int) voteRepo.countByVoteOptionId(vo.getId());
//            VoteOptionResponse voResp = new VoteOptionResponse(vo.getId(), vo.getPresentationOrder(), vo.getCaption(), numberOfVotes, isMyVote);
//            voResponse.add(voResp);
//        }
//
//        return new PollResponse(
//                poll.getId(),
//                poll.getQuestion(),
//                voResponse,
//                poll.getCreatedBy().getUsername(),
//                poll.getValidUntil() == null ? null : poll.getValidUntil().atZone(ZoneId.of("Europe/Oslo"))
//       );
//    }


    @Cacheable(cacheNames = "pollListStatic", key = "'all'")
    @Transactional(readOnly = true)
    public List<PollStaticData> getAllPollsStatic() {
        List<Poll> all = polls.findAllByOrderByPublishedAtDescIdDesc();
        return all.stream()
                .map(p -> {
                    List<VoteOptionStatic> opts = p.getOptions().stream()
                            .map(vo -> new VoteOptionStatic(
                                    vo.getId(),
                                    vo.getPresentationOrder(),
                                    vo.getCaption(),
                                    (int) voteRepo.countByVoteOptionId(vo.getId())
                            )).toList();
                    return new PollStaticData(
                            p.getId(),
                            p.getQuestion(),
                            opts,
                            p.getCreatedBy().getUsername(),
                            p.getValidUntil()
                    );
                }).toList();
    }


    public List<PollResponse> getPolls(Authentication auth, boolean onlyMyPolls) {
        User user = getUser(auth);

        List<PollStaticData> staticList;
        if (onlyMyPolls) {
            // For "my polls" we fetch fresh from DB each time; no caching
            List<Poll> my = polls.findAllByCreatedBy_IdOrderByPublishedAt(user.getId());
            staticList = my.stream()
                    .map(p -> {
                        List<VoteOptionStatic> opts = p.getOptions().stream()
                                .map(vo -> new VoteOptionStatic(
                                        vo.getId(),
                                        vo.getPresentationOrder(),
                                        vo.getCaption(),
                                        (int) voteRepo.countByVoteOptionId(vo.getId())
                                )).toList();
                        return new PollStaticData(
                                p.getId(),
                                p.getQuestion(),
                                opts,
                                p.getCreatedBy().getUsername(),
                                p.getValidUntil()
                        );
                    }).toList();
        } else {
            staticList = getAllPollsStatic();
        }

        return staticList.stream()
                .map(stat -> {
                    Long myOpt = voteRepo.findByVoterIdAndPollId(user.getId(), stat.id())
                            .map(v -> v.getVoteOption().getId())
                            .orElse(null);
                    List<VoteOptionResponse> voResp = stat.options().stream()
                            .map(voStat -> new VoteOptionResponse(
                                    voStat.id(),
                                    voStat.presentationOrder(),
                                    voStat.caption(),
                                    voStat.numberOfVotes(),
                                    myOpt != null && myOpt.equals(voStat.id())
                            ))
                            .toList();
                    return new PollResponse(
                            stat.id(),
                            stat.question(),
                            voResp,
                            stat.createdBy(),
                            stat.validUntil() == null ? null : stat.validUntil().atZone(ZoneId.of("Europe/Oslo"))
                    );
                }).toList();
    }

//    @Cacheable(cacheNames = "pollList", key = "#onlyMyPolls ? 'my:' + #auth.name : 'all'")
//    @Transactional(readOnly = true)
//    public List<PollResponse> getPolls(Authentication auth, boolean onlyMyPolls) {
//        User user = getUser(auth);
//        List<Poll> pollsToReturn = onlyMyPolls
//                ? polls.findAllByCreatedBy_IdOrderByPublishedAt(user.getId())
//                : polls.findAllByOrderByPublishedAtDescIdDesc();
//
//        List<PollResponse> response = new ArrayList<>(pollsToReturn.size());
//        for(Poll p : pollsToReturn) {
//            response.add(getPoll(auth, p.getId()));
//        }
//
//        return response;
//    }


    @Caching(evict = {
            @CacheEvict(cacheNames="pollDetail", key="#pollId"),
            @CacheEvict(cacheNames="pollList", key="'all'"),
            @CacheEvict(cacheNames="pollList", key="'my:' + #auth.name")
    })
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
            }
        } else {
            //User has not voted on this poll before
            Vote vote = new Vote();
            vote.setVoter(user);
            vote.setPoll(poll);
            vote.setVoteOption(vo);
            vote.setVotedAt(Instant.now());
            voteRepo.save(vote);
        }
    }


    //----------------- HELPERS ---------------------
    private User getUser(Authentication auth) {
        String uname = auth.getName();
        return users.findByUsername(uname)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
