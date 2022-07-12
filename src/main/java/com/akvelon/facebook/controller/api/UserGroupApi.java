package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.UserGroupDto;
import com.akvelon.facebook.dto.UserGroupPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tags(value = @Tag(name="Операции с пользовательскими группами"))
public interface UserGroupApi {

    @Operation(summary = "Добавление новой группы")
    @PostMapping("/save")
    ResponseEntity<UserGroupPage> saveUserGroup(@RequestBody @Valid UserGroupDto userGroupDto);

    @Operation(summary = "Изменени данных пользователя")
    @PostMapping("/update/{userGroupId}")
    ResponseEntity<UserGroupPage> updateUserGroup(@RequestBody @Valid UserGroupDto userGroupDto);

    @Operation(summary = "Получение всех групп")
    @GetMapping("/all")
    ResponseEntity<UserGroupPage> getAllGroups();

    @Operation(summary = "Получение группы")
    @GetMapping("/{userGroupId}")
    ResponseEntity<UserGroupPage> findById(@PathVariable("userGroupId") Long userGroupId);

    @Operation(summary = "Добавление пользователя в группу")
    @PostMapping("/{userGroupId}/add_user/{userId}")
    ResponseEntity<UserGroupPage> addUserToUserGroup(@PathVariable("userId") Long userId,
                                                     @PathVariable("userGroupId") Long userGroupId);
}
