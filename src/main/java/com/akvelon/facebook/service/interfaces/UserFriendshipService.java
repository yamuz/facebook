package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.dto.websocket.FriendshipRequestActionDto;
import com.akvelon.facebook.dto.websocket.WebsocketUserDto;

import java.util.List;

public interface UserFriendshipService {
    List<WebsocketUserDto> getActiveFriendshipRequests(Long userId);

    WebsocketUserDto createRequestForFriendship(Long userId, Long friendId);

    void actionWithFriendshipRequest(FriendshipRequestActionDto friendshipRequestActionDto);
}
