package org.valor.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class RsaJwtUtils {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final long expirationMs;

    public RsaJwtUtils(RsaKeyProvider keyProvider, @Value("${jwt.expiration-ms}") long expirationMs) {
        this.privateKey = keyProvider.getPrivateKey();
        this.publicKey = keyProvider.getPublicKey();
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(privateKey)   // подпись приватным ключом
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)  // проверка публичным ключом
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}