package no.hvl.FeedApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final String ISSUER = "feedapp";
    private final Long EXPIRY = 3600L;

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    public String authenticateAndGenerateToken(String username, String password) {
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        var principal = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();

        var jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(EXPIRY))
            .subject(principal.getUsername())
            .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)).getTokenValue();
    }

}
