package com.example.reviewsapi.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewApiConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public ReviewApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
