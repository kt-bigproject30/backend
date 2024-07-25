package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.entity.PostEntity;
import com.kt.aivle.aivleproject.service.PostService;
import com.kt.aivle.aivleproject.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private S3Service s3Service;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // DraftAI 버튼 : db 등록 save()
    @CrossOrigin
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody PostEntity postEntity) {
        return new ResponseEntity<>(postService.save(postEntity), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping("/updatePost/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image) {
        try {
            // 이미지 파일 S3에 업로드
            String imageUrl = s3Service.uploadFile(image);
            // PostEntity 업데이트
            PostEntity postEntity = new PostEntity();
            postEntity.setImageUrl(imageUrl);

            PostEntity updatedPostEntity = postService.updatePost(id, postEntity);
            return new ResponseEntity<>(updatedPostEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public ResponseEntity<?> searchPostsByTitle(@RequestBody Map<String, String> request){
        String searching = request.get("title");
        List<PostEntity> posts = postService.searchPostsByTitle(searching);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 게시판 검색기능(카테고리)
    @CrossOrigin
    @PostMapping("/search_category")
    public ResponseEntity<?> searchPostsByCategory(@RequestBody Map<String, String> request){
        String searching = request.get("category");
        List<PostEntity> posts = postService.searchPostsByCategory(searching);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }



    @CrossOrigin
    @GetMapping("/mypost/{id}")
    public ResponseEntity<List<PostEntity>> UserPost(@PathVariable("id") Long id) {
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
}