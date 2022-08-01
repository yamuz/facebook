package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.dto.mail.Mail;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.VerificationToken;
import com.akvelon.facebook.exception.EntityNotFoundException;
import com.akvelon.facebook.mq.producers.MQMessageProducer;
import com.akvelon.facebook.repository.VerificationTokenRepository;
import com.akvelon.facebook.service.interfaces.AuthService;
import com.akvelon.facebook.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final String mailFrom;

    private final String appUrl;
    private final UserService userService;
    private final MQMessageProducer mqMessageProducer;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    public AuthServiceImpl(@Value("${mail.from}") String mailFrom, @Value("${app.host}")String appUrl,
                           UserService userService, MQMessageProducer mqMessageProducer,
                           PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.mailFrom = mailFrom;
        this.appUrl = appUrl;
        this.userService = userService;
        this.mqMessageProducer = mqMessageProducer;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    private User mapToUser(RegistrationDto dto) {
        return User.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .address(dto.getAddress())
                        .phone(dto.getPhone())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .build();
    }

    @Override
    public void registration(RegistrationDto registrationDto, String urlPath) {
        if (userService.existsByEmail(registrationDto.getEmail())){
            throw new EntityNotFoundException("User already exists");
        }
        User newUser = mapToUser(registrationDto);
        UserDto userDto = userService.save(UserDto.from(newUser));
        newUser.setId(userDto.getId());

        String token = UUID.randomUUID().toString();
        createVerificationToken(newUser,token);

        String confirmationUrl = appUrl + "/api/registrationConfirm?token=" + token;
        Mail mail = Mail.builder()
                .mailFrom(mailFrom)
                .mailTo(newUser.getEmail())
                .mailSubject("Registration Confirmation")
                .mailContent(confirmationUrl)
                .build();

        mqMessageProducer.sendMessage(mail);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .expiryDate(VerificationToken.calculateExpiryDate(VerificationToken.EXPIRATION))
                .build();
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public void confirmRegistration(String token) {
        User user = Objects.requireNonNull(verificationTokenRepository.findByToken(token)).getUser();
        user.setIsactive(true);
        user.setState(User.State.CONFIRMED);
        userService.save(UserDto.from(user));
    }
}
