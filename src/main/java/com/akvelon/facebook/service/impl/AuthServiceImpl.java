package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.config.jwt.JwtAuthenticationResponse;
import com.akvelon.facebook.config.jwt.JwtProvider;
import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.dto.auth.LoginDto;
import com.akvelon.facebook.dto.auth.RegistrationDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.VerificationToken;
import com.akvelon.facebook.exception.EntityNotFoundException;
import com.akvelon.facebook.mail.OnRegistrationCompleteEvent;
import com.akvelon.facebook.repository.VerificationTokenRepository;
import com.akvelon.facebook.service.interfaces.AuthService;
import com.akvelon.facebook.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    private User mapToUser(RegistrationDto dto) {
        User user = User.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .address(dto.getAddress())
                        .phone(dto.getPhone())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .build();
        return user;
    }

    @Override
    public JwtAuthenticationResponse registration(RegistrationDto registrationDto, String urlPath) {
        if (userService.existsByEmail(registrationDto.getEmail())){
            throw new EntityNotFoundException("User already exists");
        }

        User newUser = mapToUser(registrationDto);
        UserDto userDto = userService.save(UserDto.from(newUser));
        newUser.setId(userDto.getId());

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser, Locale.ENGLISH, urlPath));

        return new JwtAuthenticationResponse("account activation link was sent to email");
    }

    @Override
    public JwtAuthenticationResponse login(LoginDto dto) {
        User user = userService.findByEmail(dto.getEmail());
        return Optional.ofNullable(dto.getPassword())
                .filter(it -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(it -> new JwtAuthenticationResponse(jwtProvider.generateToken(user.getEmail())))
                .orElseThrow(() -> new EntityNotFoundException("Password wrong"));
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
        User user = verificationTokenRepository.findByToken(token).getUser();
        user.setIsactive(true);
        userService.save(UserDto.from(user));
    }
}
