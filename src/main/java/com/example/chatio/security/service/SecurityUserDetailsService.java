package com.example.chatio.security.service;

import com.example.chatio.security.model.SecurityUser;
import com.example.chatio.security.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final SecurityUserRepository securityUserRepository;
    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not found."));
    }

}
