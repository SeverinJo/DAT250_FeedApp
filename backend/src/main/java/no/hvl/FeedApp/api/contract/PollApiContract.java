package no.hvl.FeedApp.api.contract;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Tag(name = "Poll endpoints")
@RequestMapping("/api/polls")
public interface PollApiContract {

    record PollResponse(Long id, String question, List<VoteOptionResponse> voteOptions, String createdBy, ZonedDateTime validUntil) {}
    record VoteOptionResponse(Long id, Integer presentationOrder, String caption, Integer numberOfVotes, Boolean isMyVote) {}

    record PollCreationRequest(@NotBlank String question, @NotNull @Size(min=2) List<@Valid VoteOptionCreationRequest> voteOptions) {}
    record VoteOptionCreationRequest(@NotBlank String caption) {}

    record PollStaticData(Long id, String question, List<VoteOptionStatic> options, String createdBy, Instant validUntil) {}
    record VoteOptionStatic(Long id, Integer presentationOrder, String caption, Integer numberOfVotes) {}

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get successful, polls returned")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content())
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    ResponseEntity<List<PollResponse>> getPolls(
        @Parameter(hidden = true) Authentication authentication,
        @RequestParam(name = "onlyMyPolls", required = false, defaultValue = "false") boolean onlyMyPolls
    );

    @PostMapping
    @ApiResponse(responseCode = "204", description = "Creation successful")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content())
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    ResponseEntity<Void> createPoll(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody PollCreationRequest pollCreationRequest
    );

    @PostMapping("{pollId}/vote-options/{voteOptionId}/vote")
    @ApiResponse(responseCode = "204", description = "Voted successful")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content())
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    ResponseEntity<Void> vote(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable Long pollId,
        @PathVariable Long voteOptionId
    );

}
