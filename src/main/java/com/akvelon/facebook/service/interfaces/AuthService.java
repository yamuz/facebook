package com.akvelon.facebook.service.interfaces;


import com.akvelon.facebook.config.jwt.JwtAuthenticationResponse;
import com.akvelon.facebook.dto.auth.LoginDto;
import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.VerificationToken;

public interface AuthService {
    JwtAuthenticationResponse registration(RegistrationDto registrationDto, String urlPath);
    JwtAuthenticationResponse login(LoginDto loginDto);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String verificationToken);
    void confirmRegistration(String token);
}
