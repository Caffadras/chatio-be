package com.example.chatio.security.controller;

import com.example.chatio.security.model.dto.AuthDto;
import com.example.chatio.security.model.dto.TokenDto;
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
    public ResponseEntity<TokenDto> signup(@RequestBody AuthDto authDto){
        String token = authService.signup(authDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody AuthDto authDto){
        String token = authService.signIn(authDto);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token));
    }

}
