package com.example.project1.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Neil Alishev
 */
@Component
public class JWTUtil implements InitializingBean  {
    private JwtParser jwtParser;
    private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt_secret_token}")
    private String secretToken;

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(1L).toInstant());
        Key key = Keys.hmacShaKeyFor(secretToken.getBytes());
        return Jwts
                .builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, "ADMIN")
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expirationDate)
                .compact();
    }

//    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretToken))
//                .withSubject("User details")
//                .withIssuer("Global Admin")
//                .build();
//
//        DecodedJWT jwt = verifier.verify(token);
//        return jwt.getClaim("username").asString();
//    }

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
//        if (!ObjectUtils.isEmpty(secret)) {
//            keyBytes = Decoders.BASE64.decode(secret);
//        } else {
//            secret = secretToken;
//            keyBytes = secret.getBytes();
//        }
        keyBytes = secret.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }
}
