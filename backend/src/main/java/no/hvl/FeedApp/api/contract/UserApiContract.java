package no.hvl.FeedApp.api.contract;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User endpoints")
@RequestMapping("/api/user")
public interface UserApiContract {

    record UserInformationResponse(Long id, String username, String email) {}

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get successful, user information for current user returned")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    ResponseEntity<UserInformationResponse> getUserInformation(@Parameter(hidden = true) Authentication authentication);

}
