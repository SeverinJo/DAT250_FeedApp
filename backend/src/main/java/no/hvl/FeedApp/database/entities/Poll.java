package no.hvl.FeedApp.database.entities;

import jakarta.persistence.*;
//import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
//@RedisHash
@Table(name="polls", schema="feed_app")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "poll", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("presentationOrder ASC")
    private List<VoteOption> options = new ArrayList<>();

    public Poll() {
    }

    public Poll(String question) {
        this.question = question;
    }

    public VoteOption addVoteOption(String caption) {
        VoteOption voteOption = new VoteOption();
        voteOption.setCaption(caption);
        voteOption.setPoll(this);
        voteOption.setPresentationOrder(options.size());
        this.options.add(voteOption);
        return voteOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }
}
