package com.thomas.user.account.config;

import com.thomas.common.interceptor.InternalAuthApiInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ServiceConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    InternalAuthApiInterceptor internalAuthApiInterceptor(){
        return new InternalAuthApiInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(internalAuthApiInterceptor()).addPathPatterns("/v1/internal/account/*");
    }

}
