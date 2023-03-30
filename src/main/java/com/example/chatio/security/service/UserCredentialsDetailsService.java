package com.example.chatio.security.service;

import com.example.chatio.security.model.UserCredentials;
import com.example.chatio.security.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialsDetailsService implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;
    @Override
    public UserCredentials loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not found."));
    }

}
