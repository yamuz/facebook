package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.security.jwt.JwtAuthenticationResponse;
import com.akvelon.facebook.dto.auth.LoginDto;
import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@PropertySource("classpath:application.yml")
public class AuthController {
    private final AuthService authService;
    private final String appHost;

    public AuthController(AuthService authService, @Value("${app.host}") String appHost) {
        this.authService = authService;
        this.appHost = appHost;
    }

    //@ApiOperation(value = "Регистрация пользователя", notes = "Регистрация пользователя")
    @PostMapping("/api/register")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody @Valid RegistrationDto registrationRequest) {
        return ResponseEntity.ok(authService.registration(registrationRequest, appHost));
    }


    @GetMapping("/api/registrationConfirm")
    public ResponseEntity<Map<String,String>> confirmRegistration(@RequestParam("token") String token){
        authService.confirmRegistration(token);
        return ResponseEntity.ok(Map.of("response", "Registration complete. Use /auth/login path to get tokens."));
    }
}
