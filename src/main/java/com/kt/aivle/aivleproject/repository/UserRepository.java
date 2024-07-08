package com.kt.aivle.aivleproject.repository;

import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
}
