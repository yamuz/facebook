package com.akvelon.facebook.controller.api;

import com.akvelon.facebook.dto.auth.RegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Tags(value = @Tag(name="Регистрация"))
public interface AuthApi {

    @Operation(summary = "Подтверждение регистрации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация пользователя",
                    content = { @Content(mediaType = "application/json")}
            ),
            @ApiResponse(responseCode = "404", description = "Сведения об ошибке",
                    content = { @Content(mediaType = "application/json")}
            )
    })
    @PostMapping("/api/register")
    ResponseEntity<Map<String,String>> registerUser(@Valid RegistrationDto registrationRequest);


    @Operation(summary = "Подтверждение регистрации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подтверждение регистрации пользователя",
                    content = { @Content(mediaType = "application/json")}
            ),
            @ApiResponse(responseCode = "404", description = "Сведения об ошибке",
                    content = { @Content(mediaType = "application/json")}
            )
    })
    @GetMapping("/api/registrationConfirm")
    ResponseEntity<Map<String,String>> confirmRegistration(@RequestParam("token") String token);
}
