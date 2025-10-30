package no.hvl.FeedApp.dtos;

import jakarta.validation.constraints.*;

public class UserDtos {

    public record Create(@NotBlank String username, @Email @NotBlank String email) {}

    public record Update(@Email @NotBlank String email) {}

    public record View(String username, String email) {}
}
