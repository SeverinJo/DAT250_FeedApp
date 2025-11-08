package no.hvl.FeedApp.database.repositories;

import no.hvl.FeedApp.database.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

    long countByVoteOptionId(Long voteOptionId);


    Optional<Vote> findByVoterIdAndPollId(Long userId, Long pollId);

}

