package com.example.chatio.security.service;

import com.example.chatio.security.model.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserCredentialsAuthenticationProvider implements AuthenticationProvider {

    private final UserCredentialsDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserCredentials credentials = userDetailsService.loadUserByUsername(username);
        if (!credentials.getPassword().equals(password)){
            throw  new BadCredentialsException("Invalid username or password.");
        }

        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
