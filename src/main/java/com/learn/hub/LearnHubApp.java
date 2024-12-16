package com.learn.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableDiscoveryClient
public class LearnHubApp {

    public static void main(String[] args) {
        SpringApplication.run(LearnHubApp.class, args);
    }

}
