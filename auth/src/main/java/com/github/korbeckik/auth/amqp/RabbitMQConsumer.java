package com.github.korbeckik.auth.amqp;

import com.alibaba.fastjson2.JSON;
import com.github.korbeckik.common.entity.UsersEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class RabbitMQConsumer {

    @RabbitListener(queues = {"q.user.send.email"})
    public void consume(String message) {
        UsersEntity entity = null;
        try {
            entity = JSON.parseObject(message, UsersEntity.class);
        } catch (Exception e){
            log.error(e);
        }

        if (Objects.nonNull(entity)) {
            log.info("Message received with body: {}", entity.toString());
        }
    }
}
