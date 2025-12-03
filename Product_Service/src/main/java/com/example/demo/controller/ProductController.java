package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/list")
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}/sold")
    public String markSold(@PathVariable Integer id) {
        service.markAsSold(id);
        return "Product " + id + " marked as SOLD";
    }
}
