package no.hvl.FeedApp.api.poll.dto;

import java.time.Instant;
import java.util.List;

public record PollResponse(
        Long id,
        String question,
        Instant publishedAt,
        Instant validUntil,
        Creator createdBy,
        List<OptionResponse> options) {
    public record Creator(Long id, String username) {}
    public record OptionResponse(Long id, String caption, Integer presentationOrder) {}

}
