package com.example.demo.config;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {
    public String GeneratedToken(Authentication auth) {
        String name = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWTConst.JWT_EXPIRATION);
        String token = Jwts
                .builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, JWTConst.JWT_SECRET)
                .compact();

        return token;
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(JWTConst.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWTConst.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT has expired not invalide");
        }
    }
}
