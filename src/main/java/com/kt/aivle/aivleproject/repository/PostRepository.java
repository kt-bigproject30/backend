package com.kt.aivle.aivleproject.repository;


import com.kt.aivle.aivleproject.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // 사용자 ID와 일치하는 게시글을 조회하는 메서드
    List<PostEntity> findAllByUserEntityUuid(UUID uuid);


    @Query("SELECT p FROM PostEntity p LEFT JOIN p.userEntity u ORDER BY p.createdAt DESC")
    List<PostEntity> findAllByUserEntity(@Param("uuid") UUID uuid);

    List<PostEntity> findAllByTitleContaining(String title);
    List<PostEntity> findAllByCategoryContaining(String category);
    List<PostEntity> findAllById(Long id);

    void deleteByIdAndUserEntityUuid(Long id, UUID uuid);

    @Query("SELECT p, u FROM PostEntity p LEFT JOIN p.userEntity u WHERE u.uuid = :uuid")
    List<PostEntity> findPostsByUserUuid(@Param("uuid") UUID uuid);
}
