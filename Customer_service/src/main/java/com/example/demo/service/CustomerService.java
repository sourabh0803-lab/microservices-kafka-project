package com.example.demo.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service layer for Customer Service.
 * It calls Product Service via REST and sends messages to Kafka.
 */
@Service
public class CustomerService {

    @Autowired
    private RestTemplate rest;

    @Autowired
    private KafkaTemplate<String, String> kafka;

    @Value("${product.service.url}")
    private String productUrl;

    @Value("${app.kafka.buy-topic}")
    private String topic;

    /**
     * Fetch product list from Product Service.
     * Circuit Breaker is applied to handle failures gracefully.
     */
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    public Object getProducts() {
        return rest.getForObject(productUrl, Object.class);
    }

    /**
     * Fallback method called when Product Service is down or fails repeatedly.
     */
    public Object fallback(Throwable t) {
        return "Product Service is currently unavailable. Please try again later.";
    }

    /**
     * Buy product by sending productId as message to Kafka topic.
     */
    public String buyProduct(Integer id) {
        kafka.send(topic, String.valueOf(id));
        return "Purchase request sent for Product ID: " + id;
    }
}
