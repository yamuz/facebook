package com.akvelon.facebook.security.providers;

import com.akvelon.facebook.security.jwt.ParsedToken;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface JwtProvider {
    Map<String, String> generateToken(String email, String authority );

    ParsedToken getParsedToken(String token) throws JwtException;

    Authentication buildAuthentication(String token);

    String  getTokenFromRequest(HttpServletRequest request);
}
