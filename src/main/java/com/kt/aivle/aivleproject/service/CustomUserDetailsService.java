package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.dto.CustomUserDetails;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userData = Optional.ofNullable(userRepository.findByUsername(username));

        if (userData.isPresent()) {
            return new CustomUserDetails(userData.orElse(null));
        }

        return null;
    }

}
