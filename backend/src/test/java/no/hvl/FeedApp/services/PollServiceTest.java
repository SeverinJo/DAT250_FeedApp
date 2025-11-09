package no.hvl.FeedApp.services;


import static org.assertj.core.api.Assertions.assertThat;
import no.hvl.FeedApp.api.contract.PollApiContract.*;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is an integration test for PollService that runs against a temporal/virtual PostgreSQL DB provided by testcontainers dependency
 * instead of prod DB. This is to avoid any conflict with existing data in DB and makes tests reproducible,
 * and to be able to run tests in a closed environment.
 * The TestContainer dependency runs a temporary Docker container, with schema created by Hibernate.
 * To run this test locally Docker Desktop must be running in background. In GitHub Actions docker is available by default.
 */


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PollServiceTest {

    @Autowired
    PollService pollService;

    @Autowired
    UserRepo userRepo;

    /**
     *
     * @param username
     * @return
     */
    private Authentication authentication(String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@gmail.com");
        user.setHashedPassword("$$123OlaNordman");
        userRepo.save(user);

        var auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);
        return auth;
    }

    @Test
    void createPoll_thenGetPoll() {
        var auth = authentication("Ola");

        PollCreationRequest req = new PollCreationRequest("Er dette en test?",
                List.of(new VoteOptionCreationRequest("Ja"), new VoteOptionCreationRequest("Nei")));
        Long id = pollService.createPoll(auth, req);

        var dtoResp = pollService.getPoll(auth, id);

        assertThat(dtoResp.id()).isEqualTo(id);
        assertThat(dtoResp.question()).isEqualTo("Er dette en test?");
        assertThat(dtoResp.createdBy()).isEqualTo("Ola");
        assertThat(dtoResp.voteOptions().getFirst().caption()).isEqualTo("Ja");
    }

}
