package com.studycloud1.forummaster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.studycloud1.forummaster.mapper")
public class ForumMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumMasterApplication.class, args);
    }

}
