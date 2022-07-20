package com.akvelon.facebook.mq.producers;

import com.akvelon.facebook.dto.mail.Mail;

public interface MQMessageProducer {

    void sendMessage(Mail object);
}
