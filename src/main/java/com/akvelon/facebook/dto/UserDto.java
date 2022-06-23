package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String password;
    private Boolean isactive;

    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .password(user.getPassword())
                .isactive(user.getIsactive())
                .build();
    }
}
