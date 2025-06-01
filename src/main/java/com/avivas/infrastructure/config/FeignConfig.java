package com.avivas.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer customRetryer(@Value("${feign.retry.period}") int period
                                ,@Value("${feign.retry.maxPeriod}") int maxPeriod
                                ,@Value("${feign.retry.maxAttempts}") int maxAttempts) {
        return new Retryer.Default(
            period,   
            maxPeriod,   
            maxAttempts
        );
    }
}
