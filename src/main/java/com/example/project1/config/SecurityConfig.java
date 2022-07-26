package com.example.project1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/auth/registration")
                .permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers("/auth/telegram-check")
                .hasAnyRole("TELEGRAM")
                .and()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and().formLogin().disable()
                .build();
    }

    /*        return http
                .csrf()
                .disable()
//                .authorizeExchange()
//                .anyExchange().permitAll()
                .and().build();*/
}

