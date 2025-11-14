package no.hvl.FeedApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * En Config klasse brukes til å registrere beans (objekter) i Spring-konteksten.
 * Spring oppretter dem ved oppstart og gjør dem tilgjengelig ved
 * dependency injection (@Autowired).
 *
 *        Disse beans trengs for RabbitMQ:
 *
 *        - TopicExchange.
 *        - Queue.
 *        - Binding --> Kobler queue til exchange med en routing key.
 *        - Jackson2MessageConverter --> (de)serialisering.
 *        - RabbitTemplate --> Spring-klient for å publisere.
 *
 *
 * Denne Config-klassen definerer hva som skal finnes i broker, som exchange/kø/binding, og
 * hvordan vi publiserer/leser meldinger.
 *
 * Controller/Service injiserer RabbitTemplate for å publisere, og bruker metode annotert med
 * @RabbitListener for å ta imot stemmer.
 */


//One exchange + one queue + binding
@Configuration
public class AMQPConfig {

    public static final String EXCHANGE = "polls"; //Navnet på selve RabbitMQ-exhange'en
    public static final String Q_APP_VOTES = "q.pollapp.votes"; //Navnet på køen

    @Bean
    public TopicExchange pollsExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue appVotesQueue() {
        return QueueBuilder.durable(Q_APP_VOTES).build();
    }

    // Koble køen ("q.pollapp.votes") til exchange'en ("polls").
    @Bean
    public Binding appVotesBinding(Queue appVotesQueue, TopicExchange pollsExchange) {
        return BindingBuilder.bind(appVotesQueue).to(pollsExchange).with("poll.*.vote");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper om) {
        return new Jackson2JsonMessageConverter(om);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, Jackson2JsonMessageConverter conv) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(conv);
        return t;
    }


}
