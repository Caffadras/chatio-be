package com.example.chatio.security.service.impl;

import com.example.chatio.model.UserProfile;
import com.example.chatio.repository.UserProfileRepository;
import com.example.chatio.security.exception.InvalidCredentialsException;
import com.example.chatio.security.exception.UserAlreadyExistsException;
import com.example.chatio.security.model.UserCredentials;
import com.example.chatio.security.model.dto.SignInDto;
import com.example.chatio.security.model.dto.SignUpDto;
import com.example.chatio.security.repository.UserCredentialsRepository;
import com.example.chatio.security.service.AuthService;
import com.example.chatio.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpDto signUpDto) {
        if (usernameExists(signUpDto.getUsername())){
            throw new UserAlreadyExistsException("User '" + signUpDto.getUsername() + "' already exists.");
        }

        String password = passwordEncoder.encode(signUpDto.getPassword());
        UserCredentials credentials = new UserCredentials(signUpDto.getUsername(), password);

        UserProfile userProfile = UserProfile.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .credentials(credentials)
                .build();


        userCredentialsRepository.save(credentials);
        userProfileRepository.save(userProfile);

        return jwtUtil.generateToken(credentials.getUsername());
    }

    @Override
    public String signIn(SignInDto signInDto) {
        try{
            UserCredentials credentials = (UserCredentials) userDetailsService.loadUserByUsername(signInDto.getUsername());
            if (!passwordEncoder.matches(signInDto.getPassword(), credentials.getPassword())){
                throw new InvalidCredentialsException("Username or password is invalid.");
            }
            return jwtUtil.generateToken(credentials.getUsername());
        } catch (UsernameNotFoundException e){
            throw new InvalidCredentialsException("Username or password is invalid.");
        }
    }


    public boolean usernameExists(String username){
        try{
            userDetailsService.loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e){
            return false;
        }
    }
}
