package com.example.project1.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil implements InitializingBean  {
    private JwtParser jwtParser;
    private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt_secret_token}")
    private String secretToken;
    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusHours(1L).toInstant());
        Key key = Keys.hmacShaKeyFor(secretToken.getBytes());
        return Jwts
                .builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, "ADMIN")
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expirationDate)
                .compact();
    }
    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes;
        String secret = secretToken;
        keyBytes = secret.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }
}
