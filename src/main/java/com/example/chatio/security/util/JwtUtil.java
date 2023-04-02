package com.example.chatio.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.chatio.security.exception.JwtClaimExtractionException;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

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

    public String generateToken(String username, Long userId){
        return JWT.create()
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withIssuer("chatio-be")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expirationMs))
                .sign(Algorithm.HMAC256(secret));
    }

    public void validateToken(String token) throws JwtClaimExtractionException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        verifier.verify(token);
    }


    public String extractUsername(String token) throws JwtClaimExtractionException, JWTVerificationException {
        validateToken(token);
        DecodedJWT decodedJWT = JWT.decode(token);
        String claim = decodedJWT.getClaim("username").asString();
        if (Objects.isNull(claim)){
            throw new JwtClaimExtractionException("Token does not have 'username' claim.");
        }
        return claim;
    }
}
