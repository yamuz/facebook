package com.akvelon.facebook.service.interfaces;


import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.VerificationToken;

public interface AuthService {
    void registration(RegistrationDto registrationDto, String urlPath);

    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String verificationToken);
    void confirmRegistration(String token);
}
