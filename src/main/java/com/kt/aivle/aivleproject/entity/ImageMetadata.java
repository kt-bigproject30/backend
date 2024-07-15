package com.kt.aivle.aivleproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String s3Url;

    // Additional fields can be added as needed

    public ImageMetadata() {}

    public ImageMetadata(String s3Url) {
        this.s3Url = s3Url;
    }

}
