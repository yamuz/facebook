package com.akvelon.facebook.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDto {
    @NotEmpty(message = "{loginDto.email.empty}")
    @Size(min = 9, max = 50)
    private String email;
    @Size(min = 5, max = 50)
    @NotEmpty(message = "{loginDto.password.empty}")
    private String password;
}
