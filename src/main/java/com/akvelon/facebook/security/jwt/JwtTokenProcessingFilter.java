package com.akvelon.facebook.security.jwt;

import com.akvelon.facebook.AppConstants;
import com.akvelon.facebook.security.config.SecurityConfig;
import com.akvelon.facebook.security.providers.JwtProvider;
import com.akvelon.facebook.security.providers.JwtProviderImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
@AllArgsConstructor
public class JwtTokenProcessingFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (Arrays.stream(SecurityConfig.AUTH_WHITELIST)
                .anyMatch(s -> antPathMatcher.match(s, request.getRequestURI()))) {
            filterChain.doFilter(request, response);
        } else {
            String token = jwtProvider.getTokenFromRequest(request);
            try {
                ParsedToken parsedToken = jwtProvider.getParsedToken(token);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(parsedToken.getEmail(), null,
                                Collections.singleton(new SimpleGrantedAuthority(parsedToken.getRole())));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (JwtException e) {
                //log.error(e.toString());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}
