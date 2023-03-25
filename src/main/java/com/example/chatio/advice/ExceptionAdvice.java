package com.example.chatio.advice;

import com.example.chatio.model.ErrorResponse;
import com.example.chatio.security.exception.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    public static final String INVALID_CREDENTIALS = "Invalid credentials provided.";
    @ResponseBody
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(HttpServletRequest request,
                                                                  InvalidCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), INVALID_CREDENTIALS,
                        e.getMessage(), request.getServletPath()));
    }
}
