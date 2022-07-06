package com.thomas.user.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {
    static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start auth-service");
        SpringApplication.run(AuthApplication.class, args);
    }
}