package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.entity.Post;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.service.MyContentsService;
import com.kt.aivle.aivleproject.service.PostService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mycontents")
public class MyContentsController {

    private final PostService postService;

    public MyContentsController(PostService postService) {
        this.postService = postService;
    }

//    @GetMapping("/posts")
//    public List<Post> getUserPosts(Authentication authentication) {
//        try {
//            UUID uuid = UUID.fromString(authentication.getName());
//            return postService.getPostsByUser(uuid);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Invalid UUID string: " + authentication.getName(), e);
//        }
//    }
    @GetMapping("/posts/{id}")
    public Post getPostDetails(@PathVariable Long id) {
        return postService.findById(id);
    }


//    @GetMapping("/posts/{id}")
//    public ResponseEntity<Post> getPost(@PathVariable UUID id) {
//        Post post = myContentsService.getPostDetails(id);
//        return ResponseEntity.ok(post);
//    }
//
//    @GetMapping("/myPage")
//    public String myPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        UserEntity user = memberService.getUserByUsername(username);
//
//        List<Post> posts = myContentsService.getPostsByUserId(user.getUuid());
//        model.addAttribute("posts", posts);
//
//        return "myPage";
//    }
//
//    @GetMapping("/myPage/details/{id}")
//    public String postDetails(@PathVariable UUID id, Model model) {
//        Post post = myContentsService.getPostDetails(id);
//        model.addAttribute("post", post);
//
//        return "postDetails";
//    }

}
