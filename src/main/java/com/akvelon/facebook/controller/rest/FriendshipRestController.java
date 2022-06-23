package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.dto.websocket.FriendshipRequestActionDto;
import com.akvelon.facebook.dto.websocket.WebsocketUserDto;
import com.akvelon.facebook.service.interfaces.UserFriendshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/friendship")
public class FriendshipRestController {
    private final UserFriendshipService userFriendshipService;

    @GetMapping(value = "/requests/{user_id}")
    public ResponseEntity<List<WebsocketUserDto>> getActiveRequests(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(userFriendshipService.getActiveFriendshipRequests(userId));
    }

    @PostMapping(value = "/request/action")
    public ResponseEntity<Void> actionWithFriendshipRequest(@RequestBody FriendshipRequestActionDto friendshipRequestActionDto) {
        userFriendshipService.actionWithFriendshipRequest(friendshipRequestActionDto);
        return ResponseEntity.ok().build();
    }
}
