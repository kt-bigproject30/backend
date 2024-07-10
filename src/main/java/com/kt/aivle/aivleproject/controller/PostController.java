package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.entity.Post;
import com.kt.aivle.aivleproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PostController {
    @Autowired
    private PostService postService;

    // DraftAI 버튼 : db 등록 save()
    @CrossOrigin
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    // 텍스트요약 버튼 : 텍스트 요약
    @CrossOrigin
    @GetMapping("/summarize")
    public ResponseEntity<?> summarize(@RequestBody Post post) {
        String summary = postService.summarize(post.getContents());

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
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }


    // 게시판 리스트 목록
    @CrossOrigin
    @GetMapping("/post")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    // 게시판 detail 창
    @CrossOrigin
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }
}