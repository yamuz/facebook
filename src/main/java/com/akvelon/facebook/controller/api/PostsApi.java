package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.PostDtoPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tags(value = @Tag(name="Операции с постами"))
public interface PostsApi {

    @PostMapping("/save") ResponseEntity<PostDtoPage> save(@RequestParam("mediaStream") MultipartFile file,
                                                           @RequestParam("post") String postText,
                                                           @RequestParam("ownerEmail") String ownerEmail) throws IOException;

    @Operation(summary = "Добавление поста в группу")
    @PostMapping("/saveToGroup")
    ResponseEntity<PostDtoPage> saveToGroup(@RequestParam("mediaStream") MultipartFile file,
                                     @RequestParam("post") String postText,
                                     @RequestParam("ownerEmail") String ownerEmail,
                                     @RequestParam("groupId") Long groupId) throws IOException;


    @Operation(summary = "Получение поста по id")
    @GetMapping("/{postId}")
    ResponseEntity<PostDtoPage> getPost(@PathVariable Long postId );


    @Operation(summary = "Получение всех постов пользователя")
    @GetMapping("/user/{ownerEmail}/all")
    ResponseEntity<PostDtoPage> getPostOfUser(@PathVariable String ownerEmail );

    @Operation(summary = "Удаление поста по id")
    @PostMapping("/{postId}/delete")
    ResponseEntity<String> deletePost(@PathVariable Long postId);

    @Operation(summary = "Получение постов друзей пользователя")
    @GetMapping("/user/{ownerEmail}/friends")
    ResponseEntity<PostDtoPage> getPostOfFriends(@PathVariable String ownerEmail );

    @Operation(summary = "Получение постов группы")
    @GetMapping("/user-group/{userGroupId}/all")
    ResponseEntity<PostDtoPage> getPostsByUserGroup(@PathVariable Long userGroupId );
}
