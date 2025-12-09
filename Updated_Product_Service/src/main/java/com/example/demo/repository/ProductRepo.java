package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Product;

/**
 * This interface provides CRUD operations automatically using Spring Data JPA.
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {}

