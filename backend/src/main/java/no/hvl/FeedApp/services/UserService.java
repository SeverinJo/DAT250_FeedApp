package no.hvl.FeedApp.services;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.dtos.UserDtos;
import no.hvl.FeedApp.entities.User;
import no.hvl.FeedApp.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserDtos.View registerUser (UserDtos.Create dto) {
        User entity = new User(dto.username(), dto.email());
        return toView(userRepo.save(entity));
    }

    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public User findUserByUsername (String username) {
        return userRepo.findByUsername(username);
    }

    public List<UserDtos.View> findAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toView)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private UserDtos.View toView(User user) {
        return new UserDtos.View(user.getUsername(), user.getEmail());
    }
}
