package com.example.demo.controller;

import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Customer Service.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    /**
     * GET /customer/products
     * Returns products fetched from Product Service.
     */
    @GetMapping("/products")
    public Object getProducts() {
        return service.getProducts();
    }

    /**
     * POST /customer/buy/{id}
     * Sends a buy request for given product ID via Kafka.
     */
    @PostMapping("/buy/{id}")
    public String buy(@PathVariable Integer id) {
        return service.buyProduct(id);
    }
}
