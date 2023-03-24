package com.example.chatio.security.controller;

import com.example.chatio.security.model.dto.AuthDto;
import com.example.chatio.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody AuthDto authDto){
        authService.signup(authDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
