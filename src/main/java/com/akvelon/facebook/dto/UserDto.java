package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {
    private Long id;
    @Size(min = 1, max = 30)
    private String firstName;
    @Size(min = 1, max = 30)
    private String lastName;
    @NotEmpty
    private String email;
    private String address;
    private String phone;
    private String password;
    private Boolean isactive;

    public static UserDto from(User user) {
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
