package no.hvl.FeedApp.messaging;

import no.hvl.FeedApp.config.AMQPConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventPublisher {
    private final RabbitTemplate rabbit;

    public EventPublisher(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void publishPollCreated(Long pollId, String question) {
        var event = new PollCreatedEvent(pollId, question, Instant.now());
        rabbit.convertAndSend(AMQPConfig.EXCHANGE, "poll."+pollId+".created", event);
    }

    //If userId == null ==> anonymous vote
    public void publishVote(Long pollId, Long optionId, Long userIdOrNull, boolean changed) {
        var event = new VoteEvent(pollId, optionId, userIdOrNull, Instant.now());
        var rk = "poll."+pollId+".vote"+(changed ? ".changed" : "");
        rabbit.convertAndSend(AMQPConfig.EXCHANGE, rk, event);
    }
}
