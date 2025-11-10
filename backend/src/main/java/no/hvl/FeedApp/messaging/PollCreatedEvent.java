package no.hvl.FeedApp.messaging;

import java.time.Instant;

public record PollCreatedEvent(Long pollId, String question, Instant now) {
}
