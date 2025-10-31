package no.hvl.FeedApp.api.poll.dto;

import java.time.Instant;
import java.util.List;

public record CreatePollRequest(Long creatorId, String question, List<String> options, Instant validUntil) {}
