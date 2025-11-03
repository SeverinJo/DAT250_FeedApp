package no.hvl.FeedApp.api.contract;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication endpoints")
@RequestMapping("/api/authenticate")
public interface AuthenticationApiContract {

    record AuthenticationRequest(String username, String password) {}
    record AuthenticationResponse(String accessToken, String type) {}

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Login successful, access token returned")
    @ApiResponse(responseCode = "401", description = "Invalid credentials or unauthorized access", content = @Content())
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest);

}
