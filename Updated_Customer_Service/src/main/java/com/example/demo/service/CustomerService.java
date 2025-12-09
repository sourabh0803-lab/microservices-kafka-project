package com.example.demo.service;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CustomerService
 * - Calls Product Service using WebClient.
 * - Sends BUY events to Kafka.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final WebClient productWebClient;          // injected from WebClientConfig
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String CB = "productServiceCB";  // circuit breaker name

    /**
     * Fetch ALL products via Product Service.
     */
    @CircuitBreaker(name = CB, fallbackMethod = "productListFallback")
    @Transactional(readOnly = true)
    public List<?> getAllProducts() {

        log.info("Calling Product Service: GET /products/list");

        // WebClient non-blocking hai, but hum simple .block() use kar rahe
        // to convert to normal synchronous call (easy to understand).
        List<?> products = productWebClient.get()
                .uri("/products/list")
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();

        log.info("Product Service returned {} products", products.size());
        return products;
    }

    /**
     * Fetch ONE product by ID via Product Service.
     */
    @CircuitBreaker(name = CB, fallbackMethod = "productByIdFallback")
    @Transactional(readOnly = true)
    public Object getProductById(Integer id) {

        log.info("Calling Product Service: GET /products/{}", id);

        Object product = productWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        log.info("Product Service returned product {}", id);
        return product;
    }

    /**
     * Send BUY event to Kafka.
     */
    @Transactional
    public String buyProduct(Integer id) {

        log.info("Sending Kafka BUY event for product {}", id);

        // Key & value both as String(id) – ensures ordering by same key.
        kafkaTemplate.send("buy-topic", id.toString(), id.toString());

        log.info("Kafka BUY event sent for product {}", id);

        return "Purchase request sent for product " + id;
    }

    // ------------- FALLBACKS for Circuit Breaker -------------

    // Called when getAllProducts fails (Product Service down)
    public List<?> productListFallback(Throwable ex) {
        log.error("productListFallback triggered: {}", ex.getMessage());
        return List.of("Product Service is currently unavailable");
    }

    // Called when getProductById fails
    public Object productByIdFallback(Integer id, Throwable ex) {
        log.error("productByIdFallback triggered for id={}, error={}", id, ex.getMessage());
        return "Product " + id + " details not available right now";
    }
}
