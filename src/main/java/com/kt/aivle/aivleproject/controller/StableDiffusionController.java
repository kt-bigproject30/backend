package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.service.StableDiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StableDiffusionController {
    @Value("${aws.access.key.id}")
    private String accessKey;

    @Value("${aws.secret.access.key}")
    private String secretKey;

    @Value("${cloudfront.net}")
    private String cloudName;

    @Autowired
    private StableDiffusionService StableDiffusionService;

    @PostMapping("/generate")
    public Map<String, String> generateImage(@RequestBody Map<String, String> request) {
        Map<String, String> prompts = StableDiffusionService.prompting(request);

        String positivePrompt = prompts.get("positive_prompt");
        String negativePrompt = prompts.get("negative_prompt");
        String userSelectModel = request.get("user_select_model");
        String username = prompts.get("username");

        // 1. 실제 돌아갈 코드
        Map<String, String> result = new HashMap<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "test3.py", positivePrompt, negativePrompt, userSelectModel, cloudName, username);
            processBuilder.environment().put("AWS_ACCESS_KEY_ID", accessKey);
            processBuilder.environment().put("AWS_SECRET_ACCESS_KEY", secretKey);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String[] keys = {"img1", "img2", "img3", "img4"};
            for (int i = 0; i < keys.length; i++) {
                String line = reader.readLine();
                if (line != null) {
                    result.put(keys[i], line);
                } else {
                    result.put(keys[i], "Error: No output from script");
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result.put("error", "Error: " + e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            result.put("error", "Error: " + e.getMessage());
        }
        return result;
    }
}
