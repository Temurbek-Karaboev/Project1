package com.example.project1.config;

import com.example.project1.util.JWTUtil;
import io.jsonwebtoken.JwtParser;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JWTFilter implements WebFilter {
    private JwtParser jwtParser;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private JWTUtil jwtUtil;

    public JWTFilter(JWTUtil JWTTokenProvider) {
        this.jwtUtil = JWTTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwt = resolveToken(exchange.getRequest());
        if (StringUtils.hasText(jwt) && this.jwtUtil.validateToken(jwt)) {
            Authentication authentication = this.jwtUtil.getAuthentication(jwt);
            return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
