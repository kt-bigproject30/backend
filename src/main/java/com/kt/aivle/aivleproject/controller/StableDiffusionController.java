package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.service.StableDiffusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
public class StableDiffusionController {
    @Value("${aws.access.key.id}")
    private String accessKey;

    @Value("${aws.secret.access.key}")
    private String secretKey;

    @Value("${bucket.name}")
    private String bucketName;

    @Autowired
    private StableDiffusionService StableDiffusionService;

    @PostMapping("/generate")
    public String generateImage(@RequestBody Map<String, String> request) {
        Map<String, String> prompts = StableDiffusionService.prompting(request);

        String positivePrompt = prompts.get("positive_prompt");
        String negativePrompt = prompts.get("negative_prompt");
        String userSelectModel = request.get("user_select_model"); // 변경된 부분

//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("python3", "test3.py", positivePrompt, negativePrompt, userSelectModel, bucketName);
//            processBuilder.environment().put("AWS_ACCESS_KEY_ID", accessKey);
//            processBuilder.environment().put("AWS_SECRET_ACCESS_KEY", secretKey);
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder output = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//            process.waitFor();
//
//            return output.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error generating image: " + e.getMessage();
//        }

        String result = "";
        result += positivePrompt + "\n";
        result += negativePrompt + "\n";
        result += userSelectModel + "\n";
        return result;
    }
}