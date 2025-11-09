package no.hvl.FeedApp.api.contract;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User endpoints")
@RequestMapping("/api/user")
public interface UserApiContract {

    record UserCreationRequest(@NotBlank String username, @NotBlank String email, @NotBlank @Size(min = 8) String password) {}

    record UserInformationResponse(Long id, String username, String email) {}

    @PostMapping("/new-user")
    @ApiResponse(responseCode = "201", description = "New user account created")
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content())
    ResponseEntity<UserInformationResponse> createUser(
            @RequestBody UserCreationRequest userCreationRequest
    );

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get successful, user information for current user returned")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    ResponseEntity<UserInformationResponse> getUserInformation(
            @Parameter(hidden = true) Authentication authentication
    );
}

