package no.hvl.FeedApp.configuration;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.repositories.UserRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class TestUserConfiguration {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner createTestUserRunner() {
        return args -> {
            String username = "testuser";
            if (userRepo.findByUsername(username).isEmpty()) {
                User user = new User();
                user.setUsername(username);
                user.setEmail("testuser@example.com");
                String hashed = passwordEncoder.encode("password123");
                user.setHashedPassword(hashed);
                userRepo.save(user);
                System.out.println("Created test user: " + username);
            }
        };
    }
}
