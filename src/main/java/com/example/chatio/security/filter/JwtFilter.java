package com.example.chatio.security.filter;

import com.example.chatio.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = parseToken(authHeader);
        if (Objects.isNull(token)){
            filterChain.doFilter(request, response);
        }
        else if (jwtUtil.validateToken(token)){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList())
            );
        }
    }

    private String parseToken(String header){
        if (header != null){
            String[] headerElements = header.split(" ");
            if (headerElements.length == 2 && "Bearer".equals(headerElements[1])){
                return headerElements[1];
            }
        }

        return null;
    }
}
