package com.example.chatio.security.service;

import com.example.chatio.model.UserProfile;
import com.example.chatio.security.model.dto.SignInDto;
import com.example.chatio.security.model.dto.SignUpDto;

public interface AuthService {
    String signUp(SignUpDto signUpDto);

    String signIn(SignInDto signInDto);

    UserProfile getCurrentUser();
}
