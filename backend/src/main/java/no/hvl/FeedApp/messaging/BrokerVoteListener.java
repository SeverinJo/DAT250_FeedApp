package no.hvl.FeedApp.messaging;

import no.hvl.FeedApp.config.AMQPConfig;
import no.hvl.FeedApp.services.PollService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BrokerVoteListener {

    private final PollService pollService;

    public BrokerVoteListener(PollService pollService) {
        this.pollService = pollService;
    }

    //Consume all messages that match following binding: "poll.*.vote.*"
    @RabbitListener(queues = AMQPConfig.Q_APP_VOTES)
    @Transactional
    public void onVote(VoteEvent event) {
        if(event.pollId() == null || event.optionId() == null)
            return;

        if(event.userIdOrNull() == null) {
            pollService.voteAnonymous(event.pollId(), event.pollId(), event.when());
        } else {
            pollService.voteFromBroker(event.userIdOrNull(), event.pollId(), event.optionId(), event.when());
        }
    }

}
