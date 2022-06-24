package com.akvelon.facebook.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
