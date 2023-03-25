package com.example.chatio.security.exception;

public class JwtClaimExtractionException extends RuntimeException{
    public JwtClaimExtractionException() {
        super();
    }

    public JwtClaimExtractionException(String message) {
        super(message);
    }

    public JwtClaimExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
