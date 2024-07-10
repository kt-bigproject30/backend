package com.kt.aivle.aivleproject.dto;

import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final Optional<UserEntity> userEntity;

    public CustomUserDetails(Optional<UserEntity> userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        if (userEntity.isPresent()) {
            collection.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return userEntity.get().getRoll();
                }
            });
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.map(UserEntity::getPassword).orElse(null);
    }

    @Override
    public String getUsername() {
        return userEntity.map(UserEntity::getUsername).orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
