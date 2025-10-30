package no.hvl.FeedApp.repositories;

import no.hvl.FeedApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findById (Long id);
    User findByUsername(String username);
    void deleteById(Long id);
}
