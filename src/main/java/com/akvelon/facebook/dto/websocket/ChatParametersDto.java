package com.akvelon.facebook.dto.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatParametersDto {
    private Long userId;
    private Long friendId;
}
