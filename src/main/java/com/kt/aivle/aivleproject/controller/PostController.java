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

    @CrossOrigin
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/post")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/summarize/{id}")
    public ResponseEntity<?> summarize(@PathVariable("id") Long id, @RequestBody Post post) {
        String summary = postService.summarize(id, post.getContents());

        Map<String, String> response = new HashMap<>();
        response.put("summary", summary);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/generateImage/{id}")
    public ResponseEntity<?> generateImage(@PathVariable("id") Long id, @RequestBody Map<String, String> request) {
        String summary = request.get("summary");
        String style = request.get("style");
        String imageUrl = postService.generateImage(id, summary, style);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/updatePost/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }
}