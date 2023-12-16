package com.github.korbeckik.auth.amqp;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message) {
        log.info("Send message");
        rabbitTemplate.convertAndSend("flashcards_exchange", "flashcards_key", JSON.toJSONString(message));
    }
}
