package com.thomas.user.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.thomas.user.admin")
@EnableFeignClients(basePackages = {"com.thomas.user.profile","com.thomas.user.account"})
@ComponentScan(basePackages ={"com.thomas.user.admin","com.thomas.user.profile.client","com.thomas.user.account.client"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AdminApplication {
    static final Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start admin-service");
        SpringApplication.run(AdminApplication.class, args);
    }
}

