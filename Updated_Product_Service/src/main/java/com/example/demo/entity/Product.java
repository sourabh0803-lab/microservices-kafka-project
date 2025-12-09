package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This entity class maps to the Product table in H2 database.
 */

@Data               // Lombok: generates getter/setter/toString
@NoArgsConstructor  // Lombok: generates empty constructor
@AllArgsConstructor // Lombok: generates constructor with all fields
@Entity
public class Product {

    @Id
    private Integer id;  // Primary key
    private String name; // Product name
    private String status; // available / sold
}

