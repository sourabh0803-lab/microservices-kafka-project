package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class UpdatedProductServiceApplication implements CommandLineRunner {

    private final ProductService service;

    public static void main(String[] args) {
        SpringApplication.run(UpdatedProductServiceApplication.class, args);
    }

    // Seed initial products
    public void run(String... args) {
        service.save(new Product(1, "Mobile", "available"));
        service.save(new Product(2, "Laptop", "available"));
    }
}

