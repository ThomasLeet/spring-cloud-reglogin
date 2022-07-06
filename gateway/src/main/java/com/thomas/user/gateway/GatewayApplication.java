package com.thomas.user.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = {"com.thomas.user."})
public class GatewayApplication {
    static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start gateway");
        SpringApplication.run(GatewayApplication.class, args);
    }
}

