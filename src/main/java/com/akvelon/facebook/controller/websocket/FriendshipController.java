package com.akvelon.facebook.controller.websocket;

import com.akvelon.facebook.dto.websocket.RequestDto;
import com.akvelon.facebook.dto.websocket.WebsocketUserDto;
import com.akvelon.facebook.service.interfaces.UserFriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FriendshipController {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserFriendshipService userFriendshipService;

    public static final String SEND_FRIENDSHIP = "/friend.topic/send-friendship";
    public static final String FETCH_PERSONAL_MESSAGES = "/friend.topic/user/{user_id}";

    @MessageMapping(SEND_FRIENDSHIP)
    public void sendFriendshipToUser(RequestDto requestDto) {
        sendMessage(getFetchPersonalMessagesDestination(requestDto.getRequestToUserId()),
                userFriendshipService.createRequestForFriendship(requestDto.getRequestFromUserId(), requestDto.getRequestToUserId()));
    }

    private void sendMessage(String destination, WebsocketUserDto requestFromUser) {
        messagingTemplate.convertAndSend(destination, requestFromUser);
    }

    public static String getFetchPersonalMessagesDestination(Long userId) {
        return FETCH_PERSONAL_MESSAGES.replace("{user_id}", String.valueOf(userId));
    }
}
