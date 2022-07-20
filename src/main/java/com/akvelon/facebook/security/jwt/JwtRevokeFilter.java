package com.akvelon.facebook.security.jwt;

import com.akvelon.facebook.config.AppConstants;
import com.akvelon.facebook.repository.BlackListRepository;
import com.akvelon.facebook.security.providers.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRevokeFilter extends OncePerRequestFilter {

    private final BlackListRepository blackListRepository;

    private final JwtProvider jwtProvider;
    private final AntPathRequestMatcher revokeMatcher = new AntPathRequestMatcher(AppConstants.REVOKE_TOKEN_URL,
            "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (revokeMatcher.matches(request)){
            String token = jwtProvider.getTokenFromRequest(request);
            blackListRepository.save(token);

            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
