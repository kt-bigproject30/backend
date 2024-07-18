package com.kt.aivle.aivleproject.repository;


import com.kt.aivle.aivleproject.entity.PostEntity;
import com.kt.aivle.aivleproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // 사용자 ID와 일치하는 게시글을 조회하는 메서드
//    List<PostEntity> findByUserEntityId(UUID userId);
//    Optional<PostEntity> findById(UUID id);
//    List<PostEntity> findAllById(Long id);

    List<PostEntity> findAllByUserEntityUuid(UUID uuid);
    List<PostEntity> findAllByTitleContaining(String title);

}
