package com.akvelon.facebook.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long requestFromUserId;
    private Long requestToUserId;
    private String messageText;
}
