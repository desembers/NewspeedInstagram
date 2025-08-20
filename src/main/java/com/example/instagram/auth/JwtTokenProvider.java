package com.example.instagram.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    // 값 주입
    @Value("${jwt.secretKey}")
    private String secretKey;

    // 만료시간(초 단위)
    @Value("${jwt.expiration}")
    private int expiration;

    public String getAccessToken(Long userId) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000L);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setId(UUID.randomUUID().toString())    // 토큰마다 매번 고유 ID(jti) 부여
                .claim("userId", userId)
                .signWith(key, SignatureAlgorithm.ES512)
                .setExpiration(expirationDate)
                .compact();
    }

    public Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
