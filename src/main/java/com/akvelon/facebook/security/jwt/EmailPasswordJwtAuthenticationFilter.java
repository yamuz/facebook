package com.akvelon.facebook.security.jwt;

import com.akvelon.facebook.config.AppConstants;
import com.akvelon.facebook.security.authentication.RefreshTokenAuthentication;
import com.akvelon.facebook.security.details.UserDetailsImpl;
import com.akvelon.facebook.security.providers.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class EmailPasswordJwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProviderImpl;

    public EmailPasswordJwtAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration,
                                                ObjectMapper objectMapper,
                                                JwtProvider jwtProviderImpl) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());

        this.setUsernameParameter(AppConstants.USERNAME_PARAMETER);
        this.setFilterProcessesUrl(AppConstants.AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
        this.jwtProviderImpl = jwtProviderImpl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (hasAuthorizationToken(request)) {
            String refreshToken = getToken(request);
            RefreshTokenAuthentication refreshTokenAuthentication = new RefreshTokenAuthentication(refreshToken);
            return super.getAuthenticationManager().authenticate(refreshTokenAuthentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AppConstants.AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(AppConstants.BEARER);
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AppConstants.AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(AppConstants.BEARER.length());
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        response.setContentType("application/json");

        Map<String, String> tokenJson = jwtProviderImpl.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority());

        objectMapper.writeValue(response.getOutputStream(), tokenJson);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
       response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
