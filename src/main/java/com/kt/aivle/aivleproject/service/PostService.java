package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.entity.PostEntity;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.exception.exceptions.PostNotUploadException;
import com.kt.aivle.aivleproject.repository.PostRepository;
import com.kt.aivle.aivleproject.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 서비스 화면 ==========
    public PostEntity save(PostEntity postEntity) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(username);;

        postEntity.setUserEntity(userEntity);
        return postRepository.save(postEntity);
    }

    public PostEntity updatePost(Long id, PostEntity updatedPostEntity) {
        Optional<PostEntity> postOptional = Optional.ofNullable(postRepository.findById(id).orElseThrow(PostNotUploadException::new));
        if (postOptional.isPresent()) {
            PostEntity postEntity = postOptional.get();
            postEntity.setImageUrl(updatedPostEntity.getImageUrl());
            return postRepository.save(postEntity);
        } else {
            return null;
        }
    }

    public PostEntity findById(Long id) {
        // PostRepository를 사용하여 id에 해당하는 게시물을 찾아옵니다.
        Optional<PostEntity> post = postRepository.findById(id);
        return post.orElse(null); // 게시물이 존재하지 않을 경우 null을 반환합니다.
    }

    // 게시판 검색기능
    @Transactional(readOnly = true)
    public List<PostEntity> searchPostsByTitle(String title) {
        return postRepository.findAllByTitleContaining(title);
    }

    @Transactional(readOnly = true)
    public List<PostEntity> searchPostsByCategory(String title) {
        return postRepository.findAllByCategoryContaining(title);
    }


    // 사용자 마이페이지 본인 게시물 조회
    @Transactional(readOnly = true)
    public List<PostEntity> getPostsForCurrentUser() {
        // 현재 인증된 사용자 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);

        // 해당 사용자의 UUID로 게시글 조회
        return postRepository.findAllByUserEntityUuid(user.getUuid());
    }

    @Transactional
    public List<PostEntity> getPostsAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByUsername(username);
        return postRepository.findAllByUserEntity(user.getUuid());
    }

    @Transactional(readOnly = true)
    public List<PostEntity> getIdForCurrentUser(Long id) {
        // 해당 사용자의 UUID로 게시글 조회
        return postRepository.findAllById(id);
    }


    public List<PostEntity> getPostsByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            UUID userUuid = userEntity.getUuid();
            return postRepository.findPostsByUserUuid(userUuid);
        } else {
            // 사용자 이름에 해당하는 유저가 없는 경우 빈 리스트 반환
            return List.of();
        }
    }

    @Transactional
    public void deletePost(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        postRepository.deleteByIdAndUserEntityUuid(id, user.getUuid());
    }
}