package no.hvl.FeedApp.entities;

import jakarta.persistence.*;
import no.hvl.FeedApp.entities.VoteOption;
//import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@Entity
//@RedisHash
@Table(name="votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant publishedAt;
    @ManyToOne
    private User voter;
    @ManyToOne
    private VoteOption votesOn;

    public Vote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VoteOption getOption() {
        return votesOn;
    }

    public void setOption(VoteOption option) {
        this.votesOn = option;
    }
}
