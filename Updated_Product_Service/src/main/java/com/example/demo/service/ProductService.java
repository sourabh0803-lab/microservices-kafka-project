package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductService
 * - Contains business logic related to products.
 * - Uses @Transactional for DB consistency.
 * - Uses Lombok @Slf4j for logging.
 */
@Service                   // Marks this as a Spring service bean.
@RequiredArgsConstructor   // Creates constructor for all final fields.
@Slf4j                     // Creates a `log` object for logging.
public class ProductService {

    // final => injected via constructor because of @RequiredArgsConstructor
    private final ProductRepo repo;

    /**
     * Fetch ALL products from DB.
     * readOnly = true because we are only reading.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.info("Fetching ALL products");
        List<Product> products = repo.findAll();  // JPA: SELECT * FROM product
        log.info("Fetched {} products", products.size());
        return products;
    }

    /**
     * Fetch single product by ID.
     */
    @Transactional(readOnly = true)
    public Product getProductById(Integer id) {
        log.info("Fetching product ID={}", id);

        // findById returns Optional; if empty -> throw RuntimeException
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.error("Product ID={} NOT FOUND", id);
                    return new RuntimeException("Product not found: " + id);
                });
    }

    /**
     * Mark product as SOLD.
     */
    @Transactional
    public void markAsSold(Integer id) {
        log.info("Marking product {} as SOLD", id);

        // Reuse getProductById -> centralized error handling + logging
        Product p = getProductById(id);

        p.setStatus("sold");  // modify entity in memory
        repo.save(p);         // JPA: UPDATE product SET status='sold' WHERE id=?

        log.info("Product {} successfully marked SOLD", id);
    }

    /**
     * Save / insert a new product.
     * Used at startup (CommandLineRunner) or future POST APIs.
     */
    @Transactional
    public void save(Product p) {
        log.info("Saving product {}", p.getId());
        repo.save(p);
        log.info("Product {} saved", p.getId());
    }
}
