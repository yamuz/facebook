package com.akvelon.facebook.security.config;

import com.akvelon.facebook.security.jwt.EmailPasswordJwtAuthenticationFilter;
import com.akvelon.facebook.security.jwt.JwtTokenProcessingFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsServiceImpl;

    private final AuthenticationProvider refreshTokenAuthenticationProvider;

    public static final String[] AUTH_WHITELIST = {
            "/api/register", "/api/registrationConfirm","/auth/login",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(refreshTokenAuthenticationProvider);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   EmailPasswordJwtAuthenticationFilter emailPasswordJwtAuthenticationFilter,
                                                   JwtTokenProcessingFilter jwtTokenProcessingFilter) throws Exception {

        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        httpSecurity.addFilter(emailPasswordJwtAuthenticationFilter);
        httpSecurity.addFilterBefore(jwtTokenProcessingFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }


}
