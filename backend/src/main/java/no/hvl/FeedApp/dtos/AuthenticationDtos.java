package no.hvl.FeedApp.dtos;

public class AuthenticationDtos {

    public record AuthRequest(String username, String password) {}

    public record AuthResponse(String accessToken, String type) {}

}
