package no.hvl.FeedApp.database.entities;

import jakarta.persistence.*;
//import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@Entity
//@RedisHash
@Table(name="votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant votedAt;

    @ManyToOne
    private User voter;

    @ManyToOne
    private VoteOption voteOption;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poll poll;

    public Vote() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(Instant publishedAt) {
        this.votedAt = publishedAt;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption option) {
        this.voteOption = option;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
