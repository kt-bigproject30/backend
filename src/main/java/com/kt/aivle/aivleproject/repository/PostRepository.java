package com.kt.aivle.aivleproject.repository;


import com.kt.aivle.aivleproject.entity.Post;
import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 사용자 ID와 일치하는 게시글을 조회하는 메서드
//    List<Post> findByUserEntityId(UUID userId);
//    Optional<Post> findById(UUID id);
    List<Post> findAllById(Long id);

}
