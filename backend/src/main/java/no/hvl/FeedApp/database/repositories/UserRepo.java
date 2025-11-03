package no.hvl.FeedApp.database.repositories;

import no.hvl.FeedApp.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String uname);

    void deleteByUsername(String username);
}
