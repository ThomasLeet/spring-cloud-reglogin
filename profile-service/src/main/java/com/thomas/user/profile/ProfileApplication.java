package com.thomas.user.profile;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.thomas.user.profile")
public class ProfileApplication {
    static final Logger logger = LoggerFactory.getLogger(ProfileApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start profile-service");
        SpringApplication.run(ProfileApplication.class, args);
    }
}
