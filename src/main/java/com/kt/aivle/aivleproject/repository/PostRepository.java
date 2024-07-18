package com.kt.aivle.aivleproject.repository;


import com.kt.aivle.aivleproject.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // 사용자 ID와 일치하는 게시글을 조회하는 메서드
//    List<PostEntity> findByUserEntityId(UUID userId);
//    Optional<PostEntity> findById(UUID id);

    List<PostEntity> findAllById(Long id);

}
