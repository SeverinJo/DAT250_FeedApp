package no.hvl.FeedApp.services;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.entities.User;
import no.hvl.FeedApp.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public User registerUser (User user) {
        return userRepo.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public User findUserByUsername (String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
