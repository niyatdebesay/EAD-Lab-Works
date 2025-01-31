package com.example.Security.Salon.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
@Service
public class JWTService {

    private String  secretKey = "";
    public JWTService( ) throws NoSuchAlgorithmException {
        KeyGenerator key = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = key.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    public String generateToken(UUID userId, String Role){
        Map<String , Object> claims = new HashMap<>();
        claims.put("role", Role);
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +  (60 * 60 *1000)))
                .and()
                .signWith(getKey())
                .compact();


    }

    private SecretKey getKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public  String getSubject(String token){
        return getClaims(token).getSubject();
    }
    public boolean isTokenValid(String token, String userId) {
        final String subject = getSubject(token);
        return (subject.equals(userId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public String  getClaimsRole(String token){
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
