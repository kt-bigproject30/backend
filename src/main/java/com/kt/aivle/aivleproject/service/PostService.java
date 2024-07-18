package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.entity.Post;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.exception.exceptions.PostNotUploadException;
import com.kt.aivle.aivleproject.repository.PostRepository;
import com.kt.aivle.aivleproject.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 서비스 화면 ==========
    public Post save(Post post) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(username);;

        post.setUserEntity(userEntity);
        return postRepository.save(post);
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

    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> postOptional = Optional.ofNullable(postRepository.findById(id).orElseThrow(PostNotUploadException::new));
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setImageUrl(updatedPost.getImageUrl());
            return postRepository.save(post);
        } else {
            return null;
        }
    }


    // 게시판 ==============
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        // PostRepository를 사용하여 id에 해당하는 게시물을 찾아옵니다.
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null); // 게시물이 존재하지 않을 경우 null을 반환합니다.
    }

    public List<Post> getPostsByUser(Long id) {
        return postRepository.findAllById(id);
    }

}