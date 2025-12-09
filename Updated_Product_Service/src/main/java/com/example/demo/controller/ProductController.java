package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes REST APIs for Product Service.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Product APIs", description = "Operations on product inventory")
public class ProductController {

    private final ProductService service;

    /**
     * GET /products/list
     * Returns all products.
     */
    @GetMapping("/list")
    @Operation(summary = "Get all products")
    public ResponseEntity<List<Product>> getAll() {
        log.info("REST: GET /products/list");
        return ResponseEntity.ok(service.getAllProducts());
    }

    /**
     * GET /products/{id}
     * Returns single product with given ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        log.info("REST: GET /products/{}", id);
        return ResponseEntity.ok(service.getProductById(id));
    }

    /**
     * PUT /products/{id}/sold
     * Marks product as SOLD.
     */
    @PutMapping("/{id}/sold")
    @Operation(summary = "Mark product as SOLD")
    public ResponseEntity<String> markSold(@PathVariable Integer id) {
        log.info("REST: PUT /products/{}/sold", id);
        service.markAsSold(id);
        return ResponseEntity.ok("Product " + id + " marked SOLD");
    }
}
