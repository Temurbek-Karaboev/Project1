package com.example.project1.config;

import com.example.project1.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    public SecurityConfig( JWTUtil jwtUtil ) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(getPasswordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

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
                .pathMatchers("/auth/registration", "/auth/login")
                .permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers("/auth/telegram-check")
                .hasAnyRole("TELEGRAM")
                .and()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and().formLogin().disable()
                .addFilterBefore(new JWTFilter(jwtUtil),
                        SecurityWebFiltersOrder.HTTP_BASIC)
                .build();


    }

    /*        return http
                .csrf()
                .disable()
//                .authorizeExchange()
//                .anyExchange().permitAll()
                .and().build();*/
}

