package no.hvl.FeedApp.services;


import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.poll.dto.CreatePollRequest;
import no.hvl.FeedApp.api.poll.dto.PollResponse;
import no.hvl.FeedApp.entities.Poll;
import no.hvl.FeedApp.entities.VoteOption;
import no.hvl.FeedApp.repositories.PollRepo;
import no.hvl.FeedApp.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

/*
Ikke returner JPA objekter ut fra service, returner DTOer.
Hent relasjoner via repo-metoder
 */

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepo polls;
    private final UserRepo users;

    @Transactional
    public Long createPoll(CreatePollRequest req) {
        if (req.question() == null || req.question().isBlank()) throw new IllegalArgumentException("Question required");
        if (req.options() == null || req.options().size() < 2)  throw new IllegalArgumentException("At least 2 options");

        var user = users.findById(req.creatorId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown userID: " + req.creatorId()));
        var poll = new Poll();
        poll.setCreatedBy(user);
        poll.setQuestion(req.question());
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(req.validUntil());

        for(int i = 0 ; i < req.options().size(); i++) {
            var vo = new VoteOption();
            vo.setCaption(req.options().get(i));
            vo.setPresentationOrder(i);
            vo.setPoll(poll);
            poll.getOptions().add(vo);
        }

        polls.save(poll);
        return poll.getId();
    }



    @Transactional(readOnly = true)
    public PollResponse getPoll(Long id) {
       var poll = polls.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("No polls with pollID: " + id));
       return toResponse(poll);
    }



    private PollResponse toResponse(Poll poll) {
        var options = poll.getOptions().stream()
                .sorted(Comparator.comparingInt(VoteOption::getPresentationOrder))
                .map(vo ->
                        new PollResponse.OptionResponse(vo.getId(), vo.getCaption(), vo.getPresentationOrder()
                        )).toList();

        var creator = poll.getCreatedBy() == null ? null
                : new PollResponse.Creator(poll.getCreatedBy().getId(), poll.getCreatedBy().getUsername());

        return new PollResponse(
                poll.getId(),
                poll.getQuestion(),
                poll.getPublishedAt(),
                poll.getValidUntil(),
                creator,
                options
        );

    }



    @Transactional(readOnly = true)
    public List<PollResponse> getAllPolls() {
        var list = polls.findAllByOrderByPublishedAtDescIdDesc();
        return list.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PollResponse> getPollsByCreator(Long uid) {
        if(!users.existsById(uid))
            throw new IllegalArgumentException("No user found with given uid: " + uid);

        var uPolls = polls.findAllByCreatedBy_IdOrderByPublishedAt(uid);
        return uPolls.stream().map(this::toResponse).toList();
    }

}
