package com.example.chatio.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.chatio.security.exception.JwtAuthenticationException;
import com.example.chatio.security.exception.JwtClaimExtractionException;
import com.example.chatio.security.model.UserCredentials;
import com.example.chatio.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticator {

    private final JwtUtil jwtUtil;
    private final UserCredentialsDetailsService userDetailsService;

    public void authenticate(String token) throws AuthenticationException {
        try{
            String username = jwtUtil.extractUsername(token);
            UserCredentials credentials = userDetailsService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), null, Collections.singletonList((GrantedAuthority) () -> "USER"))
            );
        } catch (JWTVerificationException | JwtClaimExtractionException | UsernameNotFoundException cause){
            //Cannot authenticate due to invalid jwt token or invalid credentials
            throw new JwtAuthenticationException("Could not authenticate with jwt token. ", cause);
        }
    }

    public void authenticateWithHeader(String authHeader) throws AuthenticationException{
        String token = parseToken(authHeader);
        if (Objects.isNull(token)){
            throw new JwtAuthenticationException("Invalid Authentication header: No jwt token was found.");
        }
        authenticate(token);
    }

    private String parseToken(String header){
        if (header != null){
            String[] headerElements = header.split(" ");
            if (headerElements.length == 2 && "Bearer".equals(headerElements[0])){
                return headerElements[1];
            }
        }

        return null;
    }
}
