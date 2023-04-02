package com.example.chatio.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if could not authenticate with provided jwt token.
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
