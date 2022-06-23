package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.dto.UserPage;
import com.akvelon.facebook.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserPage> saveAccount(@RequestBody UserDto userDto){

        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<UserPage> getAccounts(){
        return ResponseEntity
                .ok(UserPage.builder()
                        .users(userService.findAll())
                        .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserPage> findById(@PathVariable("userId") Long accountId){
        return ResponseEntity
                .ok(UserPage.builder()
                        .users(List.of(UserDto.from(userService.findById(accountId))))
                        .build());
    }
}
