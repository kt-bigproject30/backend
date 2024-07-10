package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.entity.Post;
import com.kt.aivle.aivleproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public String summarize(Long id, String contents) {
        // 텍스트 요약 로직을 여기에 추가하세요
        String summarizedText = "요약된 텍스트"; // 요약된 텍스트로 교체
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setSummary(summarizedText);
            postRepository.save(post);
        }
        return summarizedText;
    }

    public String generateImage(Long id, String summary, String style) {
        // 이미지 생성 로직을 여기에 추가하세요

        String generatedImageUrl;

        String ImageUrl1 = "https://velog.velcdn.com/images/will258/post/fe94fb83-e6a2-425b-8438-44fa5605da0b/image.png";
        String ImageUrl2 = "https://i.ytimg.com/vi/BjDCzTc0eyI/mqdefault.jpg";
        String ImageUrl3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3xYnC9RBPge60pthO9XLe7Sg8aF4Oq02xdg&s";

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
            default:
                generatedImageUrl = ImageUrl1;
                break;
        }

        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setImageUrl(generatedImageUrl);
            postRepository.save(post);
        }
        return generatedImageUrl;
    }

    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(updatedPost.getTitle());
            post.setContents(updatedPost.getContents());
            post.setSummary(updatedPost.getSummary());
            post.setImageUrl(updatedPost.getImageUrl());
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    public Post findById(Long id) {
        // PostRepository를 사용하여 id에 해당하는 게시물을 찾아옵니다.
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null); // 게시물이 존재하지 않을 경우 null을 반환합니다.
    }


}
