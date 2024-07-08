package com.kt.aivle.aivleproject.repository;

import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 회원정보 조회
    Optional<UserEntity> findByEmail(String email);
}
