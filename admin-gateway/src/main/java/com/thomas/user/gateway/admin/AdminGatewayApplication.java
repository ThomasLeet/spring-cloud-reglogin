package com.thomas.user.gateway.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminGatewayApplication {
    static final Logger logger = LoggerFactory.getLogger(AdminGatewayApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start gateway");
        SpringApplication.run(AdminGatewayApplication.class, args);
    }
}

