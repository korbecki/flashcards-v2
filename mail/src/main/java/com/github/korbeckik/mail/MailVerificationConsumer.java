package com.github.korbeckik.mail;

import com.alibaba.fastjson2.JSON;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.korbeckik.mail.entity.UsersEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailVerificationConsumer {

    private final JavaMailSender emailSender;
    private final ActivateService activateService;

    @Value("${spring.mail.username}")
    private String systemEmail;
    private static final String VERIFICATION_MAIL_PATH = "templates/verification_email.html";

    @RabbitListener(queues = {"q.user.send.email"})
    public void consume(String message) throws MessagingException, URISyntaxException {
        UsersEntity entity = parseMessage(message);
        if (Objects.nonNull(entity)) {
            log.info("Message received with body: {}", entity.toString());

            String uuid = NanoIdUtils.randomNanoId();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = FileUtils.loadFileFromResources(VERIFICATION_MAIL_PATH);
            htmlMsg = htmlMsg.replace("{uid}", uuid);
            helper.setText(htmlMsg, true);
            helper.setTo(entity.getEmail());
            helper.setSubject("Verification e-mail");
            helper.setFrom(systemEmail);

            emailSender.send(mimeMessage);
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
