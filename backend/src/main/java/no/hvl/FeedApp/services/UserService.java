package no.hvl.FeedApp.services;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    // Create
//    public UserDtos.View registerUser (UserDtos.Create dto) {
//        User entity = new User(dto.username(), dto.email());
//        return toView(userRepo.save(entity));
//    }

    // Read
//    public Optional<User> findUserById(Long id) {
//        return userRepo.findById(id);
//    }

//    public UserDtos.View findUserByUsername (String username) {
//        return toView(userRepo.findByUsername(username));
//    }

//    public List<UserDtos.View> findAllUsers() {
//        return userRepo.findAll().stream()
//                .map(this::toView)
//                .toList();
//    }

    // Update
//    public UserDtos.View updateEmail(String username, UserDtos.Update dto) {
//        User user = userRepo.findByUsername(username);
//        user.setEmail(dto.email());
//        return toView(user);
//    }

    // Delete
    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }

//    private UserDtos.View toView(User user) {
//        return new UserDtos.View(user.getUsername(), user.getEmail());
//    }
}
