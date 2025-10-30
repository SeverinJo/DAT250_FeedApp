package no.hvl.FeedApp.repositories;

import no.hvl.FeedApp.entities.Poll;
import no.hvl.FeedApp.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepo extends JpaRepository<Poll, Long> {

    /**
     * Uses @EntityGraph for eager loading of option objects related to this poll.
     * When calling this method a poll with its given options will be queried
     * @param id
     * @return
     */
    @EntityGraph(attributePaths = "options")
    Optional<Poll> findById(Long id);

    /**
     * List all polls created by user
     * @param createdById
     * @return
     */
    List<Poll> findAllByCreatedBy_Id(Long createdById);


}
