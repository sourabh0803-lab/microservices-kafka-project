package com.example.demo.controller;

import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/products")
    public Object getProducts() {
        return service.getProducts();
    }

    @PostMapping("/buy/{id}")
    public String buy(@PathVariable Integer id) {
        return service.buyProduct(id);
    }
}
