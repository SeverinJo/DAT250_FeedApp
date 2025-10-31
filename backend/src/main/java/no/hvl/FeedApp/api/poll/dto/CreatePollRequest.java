package no.hvl.FeedApp.api.poll.dto;

import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.List;

public record CreatePollRequest(
       @NotNull Long creatorId,
       @NotBlank String question,
       @Size(min = 2) List<String> options,
       Instant validUntil
) {}
