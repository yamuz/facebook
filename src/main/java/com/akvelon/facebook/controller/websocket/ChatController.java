package com.akvelon.facebook.controller.websocket;

import com.akvelon.facebook.dto.websocket.MessageDto;
import com.akvelon.facebook.dto.websocket.RequestDto;
import com.akvelon.facebook.service.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public static final String SEND_MESSAGE = "/chat.topic/send-message";
    public static final String FETCH_PERSONAL_MESSAGES = "/chat.topic/chat/{user_id}";

    @MessageMapping(SEND_MESSAGE)
    public void sendMessageToUser(RequestDto requestDto) {
        sendMessage(getFetchPersonalMessagesDestination(requestDto.getRequestToUserId()), messageService.getChatNewMessage(requestDto));
    }

    private void sendMessage(String destination, MessageDto messageDto) {
        messagingTemplate.convertAndSend(destination, messageDto);
    }

    public static String getFetchPersonalMessagesDestination(Long userId) {
        return FETCH_PERSONAL_MESSAGES.replace("{user_id}", String.valueOf(userId));
    }
}
