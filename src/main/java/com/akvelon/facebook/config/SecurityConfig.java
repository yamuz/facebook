package com.akvelon.facebook.config;

import com.akvelon.facebook.config.jwt.JwtEntryPoint;
import com.akvelon.facebook.config.jwt.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final JwtEntryPoint jwtEntryPoint;
    private static final String[] AUTH_WHITELIST = {
            "/register", "/login", "/registrationConfirm",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors()
                .and()
                  .csrf().disable()
                  .exceptionHandling()
                  .authenticationEntryPoint(jwtEntryPoint)
                .and()
                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                  .authorizeRequests()
                  .antMatchers(AUTH_WHITELIST).permitAll()
                  .anyRequest()
                  .authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
