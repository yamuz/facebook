package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.PostDtoPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tags(value = @Tag(name="операции с постами"))
public interface PostsApi {

    @Operation(summary = "Добавление поста")
    @PostMapping("/save")
    ResponseEntity<PostDtoPage> save(@RequestParam("mediaStream") MultipartFile file,
                                     @RequestParam("post") String postText,
                                     @RequestParam("ownerEmail") String ownerEmail) throws IOException;


    @Operation(summary = "Получение поста по id")
    @GetMapping("/{postId}")
    ResponseEntity<PostDtoPage> getPost(@PathVariable Long postId );


    @Operation(summary = "Получение всех постов пользователя")
    @GetMapping("/{ownerEmail}/all")
    ResponseEntity<PostDtoPage> getPostOfUser(@PathVariable String ownerEmail );

    @Operation(summary = "Удаление поста по id")
    @PostMapping("/delete/{postId}")
    ResponseEntity<String> deletePost(@PathVariable Long postId);

    @Operation(summary = "Получение постов друзей пользователя")
    @GetMapping("/{ownerEmail}/friends")
    ResponseEntity<PostDtoPage> getPostOfFriends(@PathVariable String ownerEmail );
}
