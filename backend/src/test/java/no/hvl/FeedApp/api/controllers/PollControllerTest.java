package no.hvl.FeedApp.api.controllers;


import no.hvl.FeedApp.api.contract.PollApiContract;
import no.hvl.FeedApp.services.PollService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * These tests only controller-layer of the application. That includes testing correctness of routings, that jwt is used as autenthication,
 * response codes, and that the controller actually calls the service.
 * This does not include service-tests i.e. domain logic.
 */
@WebMvcTest(PollController.class)
public class PollControllerTest {

    @Autowired
    MockMvc mvc; //Test framework that lets you send HTTP-requests without starting server

    @MockitoBean
    PollService pollService;


    @Test
    void getPolls_ok() throws Exception {
        var opt = new PollApiContract.VoteOptionResponse(10L, 0, "Ja", 3, false);
        var poll = new PollApiContract.PollResponse(1L, "Er dette en test?", List.of(opt), "Ola Nordman", ZonedDateTime.now());
        when(pollService.getPolls(any(), eq(false))).thenReturn(List.of(poll));

        mvc.perform(get("/api/polls").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].question").value("Er dette en test?"));

        verify(pollService).getPolls(any(), eq(false));
    }

    @Test
    void createPoll_created() throws Exception {
        when(pollService.createPoll(any(), any())).thenReturn(1L);

        mvc.perform(post("/api/polls").with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
          {"question":"Er dette en test?",
           "voteOptions":[{"caption":"Ja"},{"caption":"Nei"}]}
        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/polls/1"));
    }

    @Test
    void vote_noContent() throws Exception {
        mvc.perform(post("/api/polls/1/vote-options/10/vote").with(jwt()))
                .andExpect(status().isNoContent());
        verify(pollService).vote(any(), eq(1L), eq(10L));
    }

}
