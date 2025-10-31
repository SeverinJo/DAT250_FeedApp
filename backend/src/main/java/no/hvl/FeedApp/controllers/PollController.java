package no.hvl.FeedApp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.api.poll.dto.CreatePollRequest;
import no.hvl.FeedApp.services.PollService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {

    private PollService pollService;

    /**
     * @RequestBody tells Spring that the request-body should be serialized as a CreatePollRequest-DTO
     * with the help of Jackson.
     * @param req
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> create(@Valid @RequestBody CreatePollRequest req) {
        Long id = pollService.createPoll(req);
        return Map.of("pollId", id);
    }
    


}
