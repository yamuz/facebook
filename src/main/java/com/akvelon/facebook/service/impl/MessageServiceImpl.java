package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.websocket.ChatParametersDto;
import com.akvelon.facebook.dto.websocket.MessageDto;
import com.akvelon.facebook.dto.websocket.RequestDto;
import com.akvelon.facebook.entity.Message;
import com.akvelon.facebook.repository.MessageRepository;
import com.akvelon.facebook.service.interfaces.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<MessageDto> getUserChatWithFriend(ChatParametersDto chatParametersDto) {
        return messageRepository.getBySenderUserIdAndReceivedUserId(chatParametersDto.getUserId(), chatParametersDto.getFriendId())
                .stream().map(this::mapMessageToMessageDto).collect(Collectors.toList());
    }

    @Override
    public MessageDto getChatNewMessage(RequestDto requestDto) {
        return mapMessageToMessageDto(messageRepository.save(Message.builder()
                .messageText(requestDto.getMessageText())
                .senderUserId(requestDto.getRequestFromUserId())
                .receivedUserId(requestDto.getRequestToUserId())
                .build()));
    }

    private MessageDto mapMessageToMessageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .messageText(message.getMessageText())
                .sentDateTime(message.getSentDateTime())
                .receivedUserId(message.getReceivedUserId())
                .senderUserId(message.getSenderUserId())
                .build();
    }
}
