package com.kt.aivle.aivleproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class StableDiffusionController {

    @PostMapping("/generate")
    public String generateImage(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String negativePrompt = request.get("negative_prompt");
        String imgNum = request.get("img_num");
        System.out.println("잘 가나?");
        try {
            System.out.println("모델링 돌려보는 중");
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "test.py", prompt, negativePrompt, imgNum);
            processBuilder.directory(new java.io.File("/Users/choehyeonsu/Documents/spring/train/aimodel")); // Replace with your script directory
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("이거 되냐?");
            }
            process.waitFor();

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating image: " + e.getMessage();
        }
    }
}