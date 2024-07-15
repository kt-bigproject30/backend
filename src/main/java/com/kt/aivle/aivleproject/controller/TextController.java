package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.entity.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=UTF-8")
public class TextController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @GetMapping(value = "/text_summarize", consumes = "application/json; charset=UTF-8")
    public String summarizeText(@RequestBody Post post) {
        String result = "";
        try {
            if (openaiApiKey == null || openaiApiKey.isEmpty()) {
                throw new RuntimeException("OpenAI API key is not set.");
            }

            ProcessBuilder processBuilder = new ProcessBuilder("python", "C:\\Users\\User\\Downloads\\llm\\test_llm.py", post.getContents());
            processBuilder.environment().put("OPENAI_API_KEY", openaiApiKey);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return result;
    }
}