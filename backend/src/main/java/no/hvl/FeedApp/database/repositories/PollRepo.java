package no.hvl.FeedApp.database.repositories;

import no.hvl.FeedApp.database.entities.Poll;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepo extends JpaRepository<Poll, Long> {

    /**
     * Uses @EntityGraph for eager loading of option and user objects related to this poll.
     * When calling this method a poll with its given options will be queried
     * @param id
     * @return
     */
   @EntityGraph(attributePaths = {"options", "createdBy"})
   Optional<Poll> findById(Long id);


    Optional<Poll> findAllByCreatedBy_Id(Long createdById);


    @EntityGraph(attributePaths = {"options", "createdBy"})
    List<Poll> findAllByOrderByPublishedAtDescIdDesc();


    List<Poll> findAllByCreatedBy_IdOrderByPublishedAt(Long id);
}
