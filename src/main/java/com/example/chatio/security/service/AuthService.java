package com.example.chatio.security.service;

import com.example.chatio.security.model.dto.AuthDto;

public interface AuthService {
    String signup(AuthDto authDto);

    String signIn(AuthDto authDto);
}
