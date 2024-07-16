package com.kt.aivle.aivleproject.repository;

import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);

}
