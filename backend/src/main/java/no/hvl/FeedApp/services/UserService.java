package no.hvl.FeedApp.services;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.UserApiContract.*;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // Create
//    public UserDtos.View registerUser (UserDtos.Create dto) {
//        User entity = new User(dto.username(), dto.email());
//        return toView(userRepo.save(entity));
//    }

    @Transactional
    public UserInformationResponse createUser(UserCreationRequest request) {
        if (userRepo.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        String hashed = passwordEncoder .encode(request.password());
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setHashedPassword(hashed);
        userRepo.save(user);

        return new UserInformationResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public UserInformationResponse getUserInformation(Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found: " + username));

        return new UserInformationResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }


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

/*    // Delete
    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }*/

//    private UserDtos.View toView(User user) {
//        return new UserDtos.View(user.getUsername(), user.getEmail());
//    }
}
