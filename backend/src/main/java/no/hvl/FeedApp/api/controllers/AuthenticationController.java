package no.hvl.FeedApp.api.controllers;

import no.hvl.FeedApp.api.contract.AuthenticationApiContract;
import no.hvl.FeedApp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationApiContract {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authRequest) {
        var token = authenticationService.authenticateAndGenerateToken(authRequest.username(), authRequest.password());
        var response = new AuthenticationResponse(token, "bearer");
        return ResponseEntity.ok(response);
    }

}
