package com.akvelon.facebook.dto.websocket;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestDto {
    private Long requestFromUserId;
    private Long requestToUserId;
    private String messageText;
}
