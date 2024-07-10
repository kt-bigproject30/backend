package com.kt.aivle.aivleproject.repository;


import com.kt.aivle.aivleproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
