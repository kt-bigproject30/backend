package com.kt.aivle.aivleproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kt.aivle.aivleproject.dto.PostDTO;
import com.kt.aivle.aivleproject.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String imageUrl;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdAt;

    private String category;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid") // 'referencedColumnName'은 UserEntity의 PK 필드명과 일치해야 합니다.
    private UserEntity userEntity;

    //    @PrePersist
    //    protected void onCreate() {
    //        createdAt = LocalDateTime.now();
    //    }

    public static PostEntity toPostEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();
        postEntity.setImageUrl(postDTO.getImageUrl());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setCategory(postDTO.getCategory());
        postEntity.setSummary(postDTO.getSummary());
        postEntity.setContents(postEntity.getContents());
        return postEntity;
    }
}

