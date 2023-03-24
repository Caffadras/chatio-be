package com.example.chatio.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expirationMs;

    @PostConstruct
    private void encodeKey(){
        secret = Base64.encodeBase64String(secret.getBytes());
    }

    public String generateToken(String username){
        return JWT.create()
                .withIssuer(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expirationMs))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }
}
