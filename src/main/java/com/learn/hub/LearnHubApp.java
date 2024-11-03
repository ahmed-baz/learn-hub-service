package com.learn.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearnHubApp {

    public static void main(String[] args) {
        SpringApplication.run(LearnHubApp.class, args);
    }

}