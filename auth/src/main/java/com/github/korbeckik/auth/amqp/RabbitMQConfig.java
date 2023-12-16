package com.github.korbeckik.auth.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public Queue queue(){
        return new Queue("q.user.send.email");
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("flashcards_exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("flashcards_key");
    }
}
