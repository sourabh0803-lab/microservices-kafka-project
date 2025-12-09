package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Customer-facing REST APIs.
 */
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer APIs", description = "Customer operations using Product Service + Kafka")
public class CustomerController {

    private final CustomerService service;

    /**
     * GET /customer/products
     * Returns all products (via Product Service).
     */
    @GetMapping("/products")
    @Operation(summary = "Fetch all products (via Product Service)")
    public ResponseEntity<List<?>> getAll() {
        log.info("REST: GET /customer/products");
        return ResponseEntity.ok(service.getAllProducts());
    }

    /**
     * GET /customer/product/{id}
     * Returns single product.
     */
    @GetMapping("/product/{id}")
    @Operation(summary = "Fetch product by ID (via Product Service)")
    public ResponseEntity<Object> getProduct(@PathVariable Integer id) {
        log.info("REST: GET /customer/product/{}", id);
        return ResponseEntity.ok(service.getProductById(id));
    }

    /**
     * PUT /customer/buy/{id}
     * Sends BUY event to Kafka.
     */
    @PutMapping("/buy/{id}")
    @Operation(summary = "Buy product (send Kafka event)")
    public ResponseEntity<String> buyProduct(@PathVariable Integer id) {
        log.info("REST: PUT /customer/buy/{}", id);
        return ResponseEntity.ok(service.buyProduct(id));
    }
}
