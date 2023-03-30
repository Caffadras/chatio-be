package com.example.chatio.security.service;

import com.example.chatio.security.model.dto.AuthDto;
import com.example.chatio.security.model.dto.SignUpDto;

public interface AuthService {
    String signUp(SignUpDto signUpDto);

    String signIn(AuthDto authDto);
}
