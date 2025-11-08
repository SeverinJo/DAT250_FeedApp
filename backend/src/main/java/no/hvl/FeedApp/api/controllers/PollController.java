package no.hvl.FeedApp.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.PollApiContract;
import no.hvl.FeedApp.services.PollService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PollController implements PollApiContract {

    private final PollService pollService;

    @Override
    public ResponseEntity<List<PollResponse>> getPolls(Authentication authentication, boolean onlyMyPolls) {
        try {

            List<PollResponse> polls = pollService.getPolls(authentication, onlyMyPolls);
            return ResponseEntity.ok(polls);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Override
    public ResponseEntity<Void> createPoll(Authentication authentication, @Valid PollCreationRequest pollCreationRequest) {
        try {

            Long pollId = pollService.createPoll(authentication, pollCreationRequest);
            return ResponseEntity.created(URI.create("/api/polls/" + pollId)).build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> vote(Authentication authentication, Long pollId, Long voteOptionId) {
        try {

            pollService.vote(authentication, pollId, voteOptionId);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
