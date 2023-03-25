package com.example.chatio.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.chatio.security.exception.JwtClaimExtractionException;
import com.example.chatio.security.model.SecurityUser;
import com.example.chatio.security.service.SecurityUserDetailsService;
import com.example.chatio.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final SecurityUserDetailsService userDetailsService;

    private static final String SIGN_IN_ENDPOINT = "/sign-in";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = parseToken(authHeader);
        if (request.getRequestURI().endsWith(SIGN_IN_ENDPOINT) || Objects.isNull(token)){
            //user should not be able to authenticate with jwt token at sign-in endpoint
            filterChain.doFilter(request, response);
            return;
        }
        try{
            String username = jwtUtil.extractUsername(token);
            SecurityUser user = userDetailsService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList())
            );
        } catch (JWTVerificationException | JwtClaimExtractionException | UsernameNotFoundException ignored){

        }
        filterChain.doFilter(request, response);
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
