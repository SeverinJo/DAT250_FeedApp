package no.hvl.FeedApp.dtos;

import jakarta.validation.constraints.*;

import java.time.Instant;

public class VoteDtos {

    public record Cast(@NotBlank String username, @Min(1) int optionOrder) {}

    public record View(int pollId, String username, int optionOrder, Instant publishedAt) {}
}
