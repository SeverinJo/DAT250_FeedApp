package no.hvl.FeedApp.services;


import static org.assertj.core.api.Assertions.assertThat;
import no.hvl.FeedApp.api.contract.PollApiContract.*;
import no.hvl.FeedApp.database.entities.User;
import no.hvl.FeedApp.database.repositories.UserRepo;
import no.hvl.FeedApp.messaging.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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

@Testcontainers
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PollServiceTest {

    @Container
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", pg::getJdbcUrl);
        r.add("spring.datasource.username", pg::getUsername);
        r.add("spring.datasource.password", pg::getPassword);
    }

    @MockitoBean
    EventPublisher events;

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

    @Test void userVote() {
        var auth = authentication("Ola");

        var req = new PollCreationRequest("Kan du stemme på denne?",
                List.of(new VoteOptionCreationRequest("Ja"), new VoteOptionCreationRequest("Nei")));
        Long pollId = pollService.createPoll(auth, req);

        var poll = pollService.getPoll(auth, pollId);
        Long voId = poll.voteOptions().getFirst().id();
        pollService.vote(auth, pollId, voId);

        var pollAfterVoted = pollService.getPoll(auth, pollId);

        assertThat(pollAfterVoted.voteOptions().getFirst().numberOfVotes()).isEqualTo(1);
        assertThat(pollAfterVoted.voteOptions().getFirst().isMyVote()).isTrue();
        assertThat(pollAfterVoted.voteOptions().get(1).numberOfVotes()).isEqualTo(0);
    }

}
