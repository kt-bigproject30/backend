package com.kt.aivle.aivleproject.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
//                .directory("/home/ec2-user/test") // 서버에서 .env 파일이 위치한 디렉토리
                .directory("./") // 로컬에서 .env 파일이 위치한 디렉토리
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }
}