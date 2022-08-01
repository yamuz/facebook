package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.websocket.FriendshipRequestActionDto;
import com.akvelon.facebook.dto.websocket.WebsocketUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tags(value = @Tag(name="работа с запросами в друзья"))
public interface FriendshipApi {

    @Operation(summary = "Получение запросов друзья для пользователя")
    @GetMapping(value = "/requests/{user_id}")
    ResponseEntity<List<WebsocketUserDto>> getActiveRequests(@PathVariable("user_id") Long userId);

    @Operation(summary = "операции с запросами")
    @PostMapping(value = "/request/action")
    public ResponseEntity<Void> actionWithFriendshipRequest(@RequestBody FriendshipRequestActionDto friendshipRequestActionDto);
}
