package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.entity.PostEntity;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.repository.PostRepository;
import com.kt.aivle.aivleproject.repository.UserRepository;
import com.kt.aivle.aivleproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
public class PostController {
    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // DraftAI 버튼 : db 등록 save()
    @CrossOrigin
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody PostEntity postEntity) {
        return new ResponseEntity<>(postService.save(postEntity), HttpStatus.CREATED);
    }

    // 텍스트요약 버튼 : 텍스트 요약
    @CrossOrigin
    @GetMapping("/summarize")
    public ResponseEntity<?> summarize(@RequestBody PostEntity postEntity) {
        String summary = postService.summarize(postEntity.getContents());

        Map<String, String> response = new HashMap<>();
        response.put("summary", summary);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 이미지생성 버튼 : 이미지 생성
    @CrossOrigin
    @GetMapping("/generateImage")
    public ResponseEntity<?> generateImage(@RequestBody Map<String, String> request) {
        String summary = request.get("summary");
        String style = request.get("style");
        String imageUrl = postService.generateImage(summary, style);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 게시글등록 버튼 : update()
    @CrossOrigin
    @PutMapping("/updatePost/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestBody PostEntity postEntity) {
        PostEntity updatedPostEntity = postService.updatePost(id, postEntity);
        return new ResponseEntity<>(updatedPostEntity, HttpStatus.OK);
    }


    // 게시판 리스트 목록
//    @CrossOrigin
//    @GetMapping("/post")
//    public ResponseEntity<?> findAll() {
//        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
//    }

    // 게시판 detail 창
    @CrossOrigin
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }

    // 마이페이지 본인 게시물 전체 조회
    @CrossOrigin
    @GetMapping("/mypost")
    public ResponseEntity<List<PostEntity>> getPostsForCurrentUser() {
        List<PostEntity> posts = postService.getPostsForCurrentUser();
        return ResponseEntity.ok(posts);
    }


    @CrossOrigin
    @GetMapping("/post")
    public ResponseEntity<List<PostEntity>> getPostsAll() {
        List<PostEntity> posts = postService.getPostsAll();
        return ResponseEntity.ok(posts);
    }

    // 게시판 검색기능(제목)
    @CrossOrigin
    @PostMapping("/search_title")
    public ResponseEntity<?> save(@RequestBody Map<String, String> request){
        String searching = request.get("title");
        List<PostEntity> posts = postService.searchPostsByTitle(searching);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/mypost/{id}")
    public ResponseEntity<List<PostEntity>> UserPost(@PathVariable("id") Long id) {
        // 디버깅을 위해 uuid를 출력
//        System.out.println("Received UUID: " + uuid);
        List<PostEntity> posts = postService.getIdForCurrentUser(id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{username}")
    public List<PostEntity> getPostsByUsername(@PathVariable("username") String username) {
        return postService.getPostsByUsername(username);
    }

    // 게시글 삭제 기능
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id") Long id) {
            postService.deletePost(id);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
//        boolean isDeleted = postService.deletePost(id);
//        if (isDeleted) {
//            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Post not found or you are not authorized to delete this post", HttpStatus.NOT_FOUND);
//        }
//    }
}