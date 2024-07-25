package com.kt.aivle.aivleproject.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Value("${cloudfront.net}")
    private String cloudFrontName;

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

    public String uploadFile(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String keyName = "generated_images/" + multipartFile.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            String fileUrl = String.format("https://%s/%s", cloudFrontName, keyName);
            return fileUrl;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        String userHome = System.getProperty("user.home");
        String targetDirectory = userHome + "/test/picture/";
        File convFile = new File(targetDirectory + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
