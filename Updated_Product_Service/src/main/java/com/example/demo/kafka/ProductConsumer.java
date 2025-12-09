package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Kafka consumer receives "buy" messages and marks product as SOLD.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {

    private final ProductService service;

    // Kafka Listener -> called whenever message arrives on "buy-topic"
    @KafkaListener(topics = "buy-topic", groupId = "product-group")
    public void consume(String message) {
        log.info("Kafka Consumer received message={}", message);
        Integer id = Integer.parseInt(message);
        service.markAsSold(id);   // delegate to service
        log.info("Product {} marked SOLD via Kafka", id);
    }
}
