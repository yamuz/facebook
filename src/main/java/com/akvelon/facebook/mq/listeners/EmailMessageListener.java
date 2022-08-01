package com.akvelon.facebook.mq.listeners;

import com.akvelon.facebook.dto.mail.Mail;
import com.akvelon.facebook.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
@Profile("mq-on")
public class EmailMessageListener {
    private final EmailService emailService;

    @RabbitListener(queues = "#{queue.name}", containerFactory = "containerFactory")
    public void onMessage(Message message){

        try {
            Mail mail = Mail.readBytes(message.getBody());
            log.info(mail.getMailContent());
            emailService.sendMail(mail);
        } catch (Exception e){
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
