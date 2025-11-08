package no.hvl.FeedApp.database.repositories;

import no.hvl.FeedApp.database.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteOptionRepo extends JpaRepository<VoteOption, Long> {

}
