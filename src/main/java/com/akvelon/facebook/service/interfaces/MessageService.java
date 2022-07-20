package com.akvelon.facebook.service.interfaces;


import com.akvelon.facebook.dto.websocket.ChatParametersDto;
import com.akvelon.facebook.dto.websocket.MessageDto;
import com.akvelon.facebook.dto.websocket.RequestDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getUserChatWithFriend(ChatParametersDto chatParametersDto);

    MessageDto getChatNewMessage(RequestDto requestDto);
}
