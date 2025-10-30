package no.hvl.FeedApp.repositories;

import no.hvl.FeedApp.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
}
