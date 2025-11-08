package no.hvl.FeedApp.services;

import no.hvl.FeedApp.database.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user =  userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User
            .withUsername(user.getUsername())
            .password(user.getHashedPassword())
            .roles("USER")
            .build();
    }

}
