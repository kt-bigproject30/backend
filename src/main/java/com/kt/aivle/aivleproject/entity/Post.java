package com.kt.aivle.aivleproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String imageUrl;

    private LocalDateTime createdAt;
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid") // 'referencedColumnName'은 UserEntity의 PK 필드명과 일치해야 합니다.
//    @JoinColumn(name = "user_uuid", nullable = false)
    private UserEntity userEntity;

}