package com.example.demo.kafka;

import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    @Autowired
    private ProductService service;

    @KafkaListener(topics = "buy-topic", groupId = "product-group")
    public void consume(String message) {
        Integer id = Integer.parseInt(message);
        service.markAsSold(id);
        System.out.println("Kafka Consumer: Product " + id + " marked SOLD");
    }
}
