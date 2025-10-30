package no.hvl.FeedApp.dtos;

import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.List;

public class PollDtos {

    public record OptionIn(int presentationOrder, @NotBlank String caption) {}

    public record Create(@NotBlank String question, @NotNull Instant validUntil,
                         @NotBlank String username, @Size(min = 2) List<OptionIn> options) {}

    public record Update(@NotBlank String question, @NotNull Instant validUntil,
                         @Size(min = 2) List<OptionIn> options) {}

    public record OptionView(int presentationOrder, String caption) {}

    public record View(int id, String question, Instant validUntil,
                       String username, List<OptionView> options) {}

    public record Result(int presentationOrder, String caption, long votes) {}
}
