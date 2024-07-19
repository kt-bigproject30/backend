package com.kt.aivle.aivleproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class StableDiffusionService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public Map<String, String> prompting(Map<String, String> str) {
        Map<String, String> result = new HashMap<>();
        try {
            if (openaiApiKey == null || openaiApiKey.isEmpty()) {
                throw new RuntimeException("OpenAI API key is not set.");
            }

            ProcessBuilder processBuilder = new ProcessBuilder("python", "C:\\Users\\User\\Downloads\\llm\\test_prompt.py", str.get("summary"));
            // ProcessBuilder processBuilder = new ProcessBuilder("python3", "test_prompt.py", str.get("summary"));
            processBuilder.environment().put("OPENAI_API_KEY", openaiApiKey);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

            String[] keys = {"summary", "positive_prompt", "negative_prompt"};
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