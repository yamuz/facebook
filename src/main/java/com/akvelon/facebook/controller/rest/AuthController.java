package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.config.jwt.JwtAuthenticationResponse;
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
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
@Component
public class AuthController {
    private final AuthService authService;

    @Value("${app.host}")
    private final String appHost;// = "http://localhost:8080";

    //@ApiOperation(value = "Регистрация пользователя", notes = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody @Valid RegistrationDto registrationRequest) {
        return ResponseEntity.ok(authService.registration(registrationRequest, appHost));
    }

    //@ApiOperation(value = "Вход пользователя", notes = "Вход пользователя")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Map<String,String>> confirmRegistration(@RequestParam("token") String token){
        authService.confirmRegistration(token);
        return ResponseEntity.ok(Map.of("response", "Registration complete. Use /login path to login."));
    }
}
