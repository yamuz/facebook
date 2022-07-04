package com.akvelon.facebook.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Schema(description = "Данные для регистрации")
public class RegistrationDto {
    @Size(min = 9, max = 50, message = "{registrationDto.email.size}")
    private String email;

    @Schema(description = "Пароль", minLength = 5, example = "Password123")
    @Size(min = 5, max = 50, message = "registrationDto.password.size")
    private String password;
    @Size(min = 2, max = 50)
    @NotEmpty(message = "{registrationDto.firstName.empty}")
    private String firstName;
    @Size(min = 2, max = 50)
    @NotEmpty(message = "{registrationDto.lastName.empty}")
    private String lastName;
    @Size(min = 6, max = 50)
    private String phone;
    @NotEmpty(message = "{registrationDto.address.empty}")
    private String address;
}
