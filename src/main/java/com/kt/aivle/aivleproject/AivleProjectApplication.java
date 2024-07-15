package com.kt.aivle.aivleproject;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AivleProjectApplication {

	@Value("${DB_URL}")
	private String dbUrl;

	@Value("${DB_USERNAME}")
	private String dbUsername;

	@Value("${DB_PASSWORD}")
	private String dbPassword;

	@Value("${JWT_SECRET}")
	private String jwtSecret;

	@Value("${OPENAI_API_KEY}")
	private String openaiApiKey;

	@Value("${AWS_ACCESS_KEY_ID}")
	private String awsAccessKeyId;

	@Value("${AWS_SECRET_ACCESS_KEY}")
	private String awsSecretAccessKey;

	@Value("${BUCKET_NAME}")
	private String bucketName;

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		SpringApplication.run(AivleProjectApplication.class, args);
	}
}