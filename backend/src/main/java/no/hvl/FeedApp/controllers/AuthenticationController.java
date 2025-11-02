package no.hvl.FeedApp.controllers;

import no.hvl.FeedApp.dtos.AuthenticationDtos;
import no.hvl.FeedApp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDtos.AuthResponse> login(@RequestBody AuthenticationDtos.AuthRequest authRequest) {
        var token = authenticationService.authenticateAndGenerateToken(authRequest.username(), authRequest.password());
        var response = new AuthenticationDtos.AuthResponse(token, "bearer");
        return ResponseEntity.ok(response);
    }

}
