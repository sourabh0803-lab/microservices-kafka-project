package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient replaces RestTemplate for calling Product Service.
 */
@Configuration
public class WebClientConfig {

    @Value("${product.service.url}")
    private String baseUrl;

    @Bean
    public WebClient productWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl) // http://localhost:8082
                .build();
    }
}
