package com.akvelon.facebook.security.providers;

import com.akvelon.facebook.repository.BlackListRepository;
import com.akvelon.facebook.security.authentication.RefreshTokenAuthentication;
import com.akvelon.facebook.security.exceptions.RefreshTokenException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtProvider jwtProviderImpl;

    private final BlackListRepository blackListRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String)authentication.getCredentials();
        if (blackListRepository.exists(token)){
            throw new RefreshTokenException("Token was revoked");
        }
        try {
            return jwtProviderImpl.buildAuthentication(token);
        } catch (JwtException e) {
            throw new RefreshTokenException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
