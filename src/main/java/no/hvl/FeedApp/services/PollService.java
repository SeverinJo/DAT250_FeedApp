package no.hvl.FeedApp.services;

import no.hvl.FeedApp.entities.Poll;
import no.hvl.FeedApp.entities.Vote;
import no.hvl.FeedApp.entities.VoteOption;
import no.hvl.FeedApp.repositories.PollRepo;
import no.hvl.FeedApp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    @Autowired private PollRepo pollRepo;

    public VoteOption addVoteOption(Long id, String caption) {
        Poll poll = pollRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll with id " + id + " not found"));
        VoteOption voteOption = new VoteOption();
        voteOption.setCaption(caption);
        voteOption.setPoll(poll);
        voteOption.setPresentationOrder(poll.getOptions().size());
        poll.getOptions().add(voteOption);
        return voteOption;
    }

}
