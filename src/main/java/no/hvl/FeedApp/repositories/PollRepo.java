package no.hvl.FeedApp.repositories;

import no.hvl.FeedApp.entities.Poll;
import no.hvl.FeedApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepo extends JpaRepository<Poll, Integer> {
}
