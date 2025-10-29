package no.hvl.FeedApp.services;

import no.hvl.FeedApp.entities.Poll;
import no.hvl.FeedApp.entities.User;
import no.hvl.FeedApp.entities.Vote;
import no.hvl.FeedApp.entities.VoteOption;
import no.hvl.FeedApp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepo userRepo;

    public User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        userRepo.save(user);
        return user;
    }

    public Poll createPoll(Long id, String question) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll with id " + id + " not found"));
        Poll poll = new Poll(question);
        poll.setCreatedBy(user);
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.parse("2025-12-31T23:59:59.00Z"));
        user.getCreated().add(poll);
        return poll;
    }

    public Vote voteFor(Long id, VoteOption option) {
        Optional<User> user = userRepo.findById(id);
        Vote vote = new Vote();
        vote.setPublishedAt(Instant.now());
        vote.setVoter(user.orElse(null));
        vote.setOption(option);
        return vote;
    }
}
