package com.example.chatio.security.filter;

import com.example.chatio.security.exception.JwtAuthenticationException;
import com.example.chatio.security.service.JwtTokenAuthenticator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenAuthenticator jwtTokenAuthenticator;

    private static final String SIGN_IN_ENDPOINT = "/sign-in";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (request.getRequestURI().endsWith(SIGN_IN_ENDPOINT) || Objects.isNull(authHeader)){
            //user should not be able to authenticate with jwt token at sign-in endpoint
            filterChain.doFilter(request, response);
            return;
        }
        try{
            jwtTokenAuthenticator.authenticateWithHeader(authHeader);
        } catch (JwtAuthenticationException ignored){
            //could not authenticate, but no action is required
        }
        filterChain.doFilter(request, response);
    }
}
