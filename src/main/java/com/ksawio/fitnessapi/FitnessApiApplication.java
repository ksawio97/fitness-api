package com.ksawio.fitnessapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FitnessApiApplication {
    @Value("#{'${custom.security.allowed_origins}'.split(',')}")
    private String[] allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("NullableProblems") CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(FitnessApiApplication.class, args);
    }

}
