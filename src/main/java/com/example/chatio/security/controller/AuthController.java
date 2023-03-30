package com.example.chatio.security.controller;

import com.example.chatio.security.model.dto.SignInDto;
import com.example.chatio.security.model.dto.SignUpDto;
import com.example.chatio.security.model.dto.TokenDto;
import com.example.chatio.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/sign-up")
    public ResponseEntity<TokenDto> signUp(@RequestBody SignUpDto signUpDto){
        String token = authService.signUp(signUpDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDto(token));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto){
        String token = authService.signIn(signInDto);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token));
    }

    @GetMapping("/logCheck")
    public ResponseEntity<Void> isLoggedIn(){
        return ResponseEntity.ok().build();
    }

}
