package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.dto.FriendshipRequestAction;
import com.akvelon.facebook.dto.UserDto;

import java.util.List;

public interface UserFriendshipService {
    List<UserDto> getActiveFriendshipRequests(Long userId);

    UserDto createRequestForFriendship(Long userId, Long friendId);

    void actionWithFriendshipRequest(FriendshipRequestAction friendshipRequestAction);
}
