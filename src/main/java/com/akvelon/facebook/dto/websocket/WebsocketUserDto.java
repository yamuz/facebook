package com.akvelon.facebook.dto.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebsocketUserDto {
    private Long id;
    private String firstName;
    private String lastName;
}
