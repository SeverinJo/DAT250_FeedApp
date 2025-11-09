package no.hvl.FeedApp.api.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.UserApiContract;
import no.hvl.FeedApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiContract {

    private final UserService userService;

    @Override
    public ResponseEntity<UserInformationResponse> createUser(
            @RequestBody @Valid UserCreationRequest request) {

        UserInformationResponse created = userService.createUser(request);

        URI location = URI.create("/api/user/" + created.id());
        return ResponseEntity
                .created(location)
                .body(created);

    }

    @Override
    public ResponseEntity<UserInformationResponse> getUserInformation(Authentication authentication) {
        UserInformationResponse userInfo = userService.getUserInformation(authentication);
        return ResponseEntity.ok(userInfo);
    }

}
