package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepo repo;

    @Test
    void testMarkAsSold() {
        Product p = new Product(1, "Mobile", "available");
        when(repo.findById(1)).thenReturn(Optional.of(p));

        productService.markAsSold(1);

        assertEquals("sold", p.getStatus());
        verify(repo, times(1)).save(p);
    }
}
