package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.dto.PostDTO;
import com.kt.aivle.aivleproject.dto.UserDTO;
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

    public String summarize(String contents) {
        // 텍스트 요약 로직을 여기에 추가하세요
        String summarizedText = "요약된 텍스트"; // 요약된 텍스트로 교체
        return summarizedText;
    }

    public String generateImage(String summary, String style) {
        // 이미지 생성 로직을 여기에 추가하세요

        String generatedImageUrl;

        String ImageUrl1 = "https://velog.velcdn.com/images/will258/post/fe94fb83-e6a2-425b-8438-44fa5605da0b/image.png";
        String ImageUrl2 = "https://i.ytimg.com/vi/BjDCzTc0eyI/mqdefault.jpg";
        String ImageUrl3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3xYnC9RBPge60pthO9XLe7Sg8aF4Oq02xdg&s";
        String ImageUrl4 = "https://velog.velcdn.com/images/will258/post/fe94fb83-e6a2-425b-8438-44fa5605da0b/image.png";

        switch (style) {
            case "style1":
                generatedImageUrl = ImageUrl1;
                break;
            case "style2":
                generatedImageUrl = ImageUrl2;
                break;
            case "style3":
                generatedImageUrl = ImageUrl3;
                break;
            case "style3=4":
                generatedImageUrl = ImageUrl4;
                break;
            default:
                generatedImageUrl = ImageUrl1;
                break;
        }
        return generatedImageUrl;
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


    // 게시판 ==============
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public PostEntity findById(Long id) {
        // PostRepository를 사용하여 id에 해당하는 게시물을 찾아옵니다.
        Optional<PostEntity> post = postRepository.findById(id);
        return post.orElse(null); // 게시물이 존재하지 않을 경우 null을 반환합니다.
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

//    @Transactional(readOnly = true)
//    public List<PostDTO> findByUserUuid(UUID uuid) {
//        List<PostDTO> postDTO = postRepository.findByUserUuid(UUID uuid);
//        return postDTO;
//    }

//    @Transactional(readOnly = true)
//    public List<PostEntity> findByUserUuid() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserEntity user = userRepository.findByUsername(username);
//
//        return postRepository.findByUserUuid(user.getUuid());
//    }

//    public List<PostDTO> getPostsByUserUuid(UUID uuid) {
//        return postRepository.findByUserUuid(uuid);
//    }

}