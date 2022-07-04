package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.controller.api.AuthApi;
import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@PropertySource("classpath:application.yml")
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final String appHost;

    public AuthController(AuthService authService, @Value("${app.host}") String appHost) {
        this.authService = authService;
        this.appHost = appHost;
    }

    @PostMapping("/api/register")
    public ResponseEntity<Map<String,String>> registerUser( @Valid RegistrationDto registrationDto) {
        authService.registration(registrationDto, appHost);
        return ResponseEntity.ok(Map.of("response", "account activation link was sent to email"));
    }


    @GetMapping("/api/registrationConfirm")
    public ResponseEntity<Map<String,String>> confirmRegistration(@RequestParam("token") String token){
        authService.confirmRegistration(token);
        return ResponseEntity.ok(Map.of("response", "Registration complete. Use /auth/login path to get tokens."));
    }
}
