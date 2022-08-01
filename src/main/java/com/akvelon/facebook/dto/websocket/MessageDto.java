package com.akvelon.facebook.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private String messageText;
    private LocalDateTime sentDateTime;
    private Long receivedUserId;
    private Long senderUserId;
}
