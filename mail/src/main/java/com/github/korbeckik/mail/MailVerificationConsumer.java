package com.github.korbeckik.mail;

import com.alibaba.fastjson2.JSON;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.korbeckik.mail.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailVerificationConsumer {

    private final MailService mailService;
    private final ActivateService activateService;

    @Value("${spring.mail.username}")
    private String systemEmail;

    @RabbitListener(queues = {"q.user.send.email"})
    public void consume(String message) {
        UsersEntity entity = parseMessage(message);
        if (Objects.nonNull(entity)) {
            log.info("Message received with body: {}", entity.toString());

            String uuid = NanoIdUtils.randomNanoId();
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(systemEmail);
            simpleMailMessage.setTo(entity.getEmail());
            simpleMailMessage.setSubject("Activation e-mail");
            simpleMailMessage.setText("Activate your e-mail with code" + uuid);

            mailService.sendEmail(simpleMailMessage);
            activateService.markAsSend(entity.getId(), uuid);

        }
    }

    private static UsersEntity parseMessage(String message) {
        UsersEntity entity = null;
        try {
            entity = JSON.parseObject(message, UsersEntity.class);
        } catch (Exception e){
            log.error(e);
        }
        return entity;
    }
}
