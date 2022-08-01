package com.akvelon.facebook.mq.producers;

import com.akvelon.facebook.dto.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.akvelon.facebook.mq.config.MqConstants.FILES_EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailMessageProducer implements MQMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String routing = "files.email";

    @Override
    public void sendMessage(Mail mail) {
        log.info("EmailMessageProducer:" + mail.getMailContent());
        rabbitTemplate.send(FILES_EXCHANGE_NAME, routing,  new Message(Mail.convertToBytes(mail)));
    }
}
