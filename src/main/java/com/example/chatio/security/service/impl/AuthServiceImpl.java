package com.example.chatio.security.service.impl;

import com.example.chatio.security.exception.InvalidCredentialsException;
import com.example.chatio.security.exception.UserAlreadyExistsException;
import com.example.chatio.security.model.SecurityUser;
import com.example.chatio.security.model.dto.AuthDto;
import com.example.chatio.security.repository.SecurityUserRepository;
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
    private final SecurityUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signup(AuthDto authDto) {
        if (usernameExists(authDto.getUsername())){
            throw new UserAlreadyExistsException("User '" + authDto.getUsername() + "' already exists.");
        }

        String password = passwordEncoder.encode(authDto.getPassword());
        SecurityUser user = new SecurityUser(authDto.getUsername(), password);
        userRepository.save(user);
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public String signIn(AuthDto authDto) {
        try{
            SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(authDto.getUsername());
            if (!passwordEncoder.matches(authDto.getPassword(), user.getPassword())){
                throw new InvalidCredentialsException("Username or password is invalid.");
            }
            return jwtUtil.generateToken(user.getUsername());
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
