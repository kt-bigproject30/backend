package com.kt.aivle.aivleproject.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Paths;
import java.util.List;

@Service
public class S3Service {

    @Value("${aws.access.key.id}")
    private String accessKeyId;

    @Value("${aws.secret.access.key}")
    private String secretAccessKey;

    @Value("${bucket.name}")
    private String bucketName;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 서울 리전 - 대문자로만 입력
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(String filePath) {
        String keyName = Paths.get(filePath).getFileName().toString();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(putObjectRequest, Paths.get(filePath));
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(keyName)).toExternalForm();
    }

    public List<S3Object> listFiles() {
        return s3Client.listObjectsV2(builder -> builder.bucket(bucketName)).contents();
    }
}
