package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.dto.UserPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tags(value = @Tag(name="Операции с пользователем"))
public interface UserApi {

    @Operation(summary = "Добавление нового пользователя")
    @PostMapping("/save")
    ResponseEntity<UserPage> saveAccount(@RequestBody @Valid UserDto userDto);

    @Operation(summary = "Изменени данных пользователя")
    @PostMapping("/{userId}/update")
    ResponseEntity<UserPage> updateAccount(@RequestBody @Valid UserDto userDto);

    @Operation(summary = "Получение всех пользователей")
    @GetMapping("/all")
    ResponseEntity<UserPage> getAccounts();

    @Operation(summary = "Получение пользователя")
    @GetMapping("/{userId}")
    ResponseEntity<UserPage> findById(@PathVariable("userId") Long accountId);
}
