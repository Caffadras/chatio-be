package com.example.chatio.security.service.impl;

import com.example.chatio.security.model.SecurityUser;
import com.example.chatio.security.model.dto.AuthDto;
import com.example.chatio.security.repository.SecurityUserRepository;
import com.example.chatio.security.service.AuthService;
import com.example.chatio.security.service.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SecurityUserDetailsService userDetailsService;

    private final SecurityUserRepository userRepository;

    @Override
    public void signup(AuthDto authDto) {
        if (usernameExists(authDto.getUsername())){
            return;
        }

        SecurityUser user = new SecurityUser(authDto.getUsername(), authDto.getPassword());
        userRepository.save(user);
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
