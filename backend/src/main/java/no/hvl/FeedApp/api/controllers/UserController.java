package no.hvl.FeedApp.api.controllers;


import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.UserApiContract;
import no.hvl.FeedApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiContract {

    private final UserService service;

    @Override
    public ResponseEntity<UserInformationResponse> getUserInformation(Authentication authentication) {
        // todo: implement controller logic
        // todo: Should give 200
        return null;
    }

}
