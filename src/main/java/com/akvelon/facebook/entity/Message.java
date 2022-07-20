package com.akvelon.facebook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @Column(name = "received_user_id")
    private Long receivedUserId;

    @Column(name = "sender_user_id")
    private Long senderUserId;

}
