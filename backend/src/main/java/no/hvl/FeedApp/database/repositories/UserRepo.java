package no.hvl.FeedApp.database.repositories;

import no.hvl.FeedApp.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String uname);

    void deleteByUsername(String username);
}
