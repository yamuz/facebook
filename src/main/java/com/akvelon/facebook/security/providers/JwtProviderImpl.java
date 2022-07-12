package com.akvelon.facebook.security.providers;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.security.details.UserDetailsImpl;
import com.akvelon.facebook.security.jwt.ParsedToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtDurationDaysAccess}")
    private Integer jwtDurationDaysAccess;

    @Value("${app.jwtDurationDaysRefresh}")
    private Integer jwtDurationDaysRefresh;
    @Override
    public Map<String, String> generateToken(String email, String authority ) {
        Date dateAccess = Date.from(LocalDate.now().plusDays(jwtDurationDaysAccess).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateRefresh = Date.from(LocalDate.now().plusDays(jwtDurationDaysRefresh).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken",
                Jwts.builder()
                .setSubject(email)
                .setExpiration(dateAccess)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setClaims(Map.of("role", authority))
                .compact());
        tokens.put("refreshToken",
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(dateRefresh)
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .setClaims(Map.of("role", authority))
                        .compact());
        return tokens;
    }


    @Override
    public ParsedToken getParsedToken(String token) throws JwtException{
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return ParsedToken.builder()
                .email(claims.getSubject())
                .role((String) claims.get("role"))
                .build();

    }

    @Override
    public Authentication buildAuthentication(String token) {
        ParsedToken parsedToken = getParsedToken(token);

        return new UsernamePasswordAuthenticationToken(new UserDetailsImpl(
                User.builder()
                        .role(User.Role.valueOf(parsedToken.getRole()))
                        .email(parsedToken.getEmail())
                        .build()
                ),
                null,
                Collections.singleton(new SimpleGrantedAuthority(parsedToken.getRole())));
    }
}
