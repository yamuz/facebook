package com.akvelon.facebook.service.impl;


import com.akvelon.facebook.dto.websocket.FriendshipRequestActionDto;
import com.akvelon.facebook.dto.websocket.WebsocketUserDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserFriendship;
import com.akvelon.facebook.repository.UserFriendshipRepository;
import com.akvelon.facebook.service.interfaces.UserFriendshipService;
import com.akvelon.facebook.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.akvelon.facebook.enumr.FriendshipActionEnum.*;

@Service
@AllArgsConstructor
public class UserFriendshipServiceImpl implements UserFriendshipService {
    private final UserFriendshipRepository userFriendshipRepository;
    private final UserService userService;

    @Override
    public List<WebsocketUserDto> getActiveFriendshipRequests(Long userId) {
        return userFriendshipRepository.getUserAllByToUserIdAndAction(userId,WAITING.getDescription()).stream()
                .map(this::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public WebsocketUserDto createRequestForFriendship(Long userId, Long friendId) {
        if (!userFriendshipRepository.existsByFromUserIdAndToUserId(userId, friendId)) {
            userFriendshipRepository.save(UserFriendship.builder()
                    .fromUserId(userId)
                    .toUserId(friendId)
                    .action(WAITING.getDescription())
                    .actionDate(LocalDateTime.now())
                    .build());
            return mapUserToUserDto(userService.findById(userId));
        }
        throw new RuntimeException("Friendship request send");
    }

    @Override
    public void actionWithFriendshipRequest(FriendshipRequestActionDto friendshipRequestActionDto) {
        UserFriendship userFriendship = userFriendshipRepository.getByFromUserIdAndToUserId(
                        friendshipRequestActionDto.getRequestFromUserId(),
                        friendshipRequestActionDto.getRequestToUserId())
                .orElseThrow(RuntimeException::new);
        userFriendship.setAction(friendshipRequestActionDto.getAction().getDescription());
        userFriendship.setActionDate(LocalDateTime.now());
        userFriendshipRepository.save(userFriendship);
    }

    private WebsocketUserDto mapUserToUserDto(User user) {
        return WebsocketUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}