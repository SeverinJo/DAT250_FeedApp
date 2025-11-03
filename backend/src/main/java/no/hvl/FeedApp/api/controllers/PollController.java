package no.hvl.FeedApp.api.controllers;

import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.contract.PollApiContract;
import no.hvl.FeedApp.services.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PollController implements PollApiContract {

    private PollService pollService;

    @Override
    public ResponseEntity<List<PollResponse>> getPolls(Authentication authentication, boolean onlyMyPolls) {
        // todo: implement controller logic
        // todo: Should give 200 or 400
        return null;
    }

    @Override
    public ResponseEntity<Void> createPoll(Authentication authentication, PollCreationRequest pollCreationRequest) {
        // todo: implement controller logic
        // todo: Should give 204 or 400
        return null;
    }

    @Override
    public ResponseEntity<Void> vote(Authentication authentication, Long pollId, Long voteOptionId) {
        // todo: implement controller logic
        // todo: remember that this endpoint should be used to vote initially AND to update the users existing vote
        // todo: Should give 204 or 400
        return null;
    }


}
