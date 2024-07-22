package com.kt.aivle.aivleproject.dto;

import com.kt.aivle.aivleproject.entity.PostEntity;
import com.kt.aivle.aivleproject.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String category;
    private UUID uuid;
    private String summary;
    private String imageUrl;

    public static PostDTO toPostDTO(PostEntity postEntity, UserEntity userEntity) {

        UserDTO userDTO = new UserDTO();
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        userDTO.setUuid(userEntity.getUuid());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setCreatedAt(postEntity.getCreatedAt());
        postDTO.setCategory(postEntity.getCategory());
        postDTO.setSummary(postEntity.getSummary());
        postDTO.setImageUrl(postEntity.getImageUrl());
        return postDTO;
    }


    public PostDTO(Long id, String title, LocalDateTime createdAt, String category) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.category = category;
    }

}
