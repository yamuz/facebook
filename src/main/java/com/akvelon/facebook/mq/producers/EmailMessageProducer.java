package com.akvelon.facebook.mq.producers;

import com.akvelon.facebook.dto.mail.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.akvelon.facebook.mq.config.MqConstants.FILES_EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
public class EmailMessageProducer implements MQMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String routing = "email";

    @Override
    public void sendMessage(Mail mail) {
        rabbitTemplate.send(FILES_EXCHANGE_NAME, routing,  new Message(Mail.convertToBytes(mail)));
    }
}
