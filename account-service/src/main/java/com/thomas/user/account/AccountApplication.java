package com.thomas.user.account;

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
@MapperScan("com.thomas.user.account")
@EnableFeignClients(basePackages = {"com.thomas.user.profile"})
@ComponentScan(basePackages ={"com.thomas.user.account","com.thomas.user.profile.client"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AccountApplication {
    static final Logger logger = LoggerFactory.getLogger(AccountApplication.class);

    public static void main(String[] args) {
        logger.info("========>>>>>>>> start account-service");
        SpringApplication.run(AccountApplication.class, args);
    }
}

