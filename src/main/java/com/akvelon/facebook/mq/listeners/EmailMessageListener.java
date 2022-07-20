package com.akvelon.facebook.mq.listeners;

import com.akvelon.facebook.dto.mail.Mail;
import com.akvelon.facebook.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageListener {
    private final EmailService emailService;

    @RabbitListener(queues = "#{queue.name", containerFactory = "containerFactory")
    public void onMessage(Message message){
        try {
            Mail mail = Mail.readBytes(message.getBody());
            emailService.sendMail(mail);
        } catch (Exception e){
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
