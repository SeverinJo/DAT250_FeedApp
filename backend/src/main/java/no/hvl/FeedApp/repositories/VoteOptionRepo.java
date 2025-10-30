package no.hvl.FeedApp.repositories;

import no.hvl.FeedApp.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteOptionRepo extends JpaRepository<VoteOption, Long> {
}
