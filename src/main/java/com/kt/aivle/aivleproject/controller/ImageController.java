package com.kt.aivle.aivleproject.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
public class ImageController {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @PostMapping("/uploadImage")
    public String uploadImage() {
        try {
            Process process = Runtime.getRuntime().exec("python test2.py");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
            return "Image uploaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading image.";
        }
    }
}