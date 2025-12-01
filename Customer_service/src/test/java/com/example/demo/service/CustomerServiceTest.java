package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private RestTemplate rest;

    @Mock
    private KafkaTemplate<String, String> kafka;

    @Test
    void testGetProducts_Success() {
        Object mockResponse = "mock-products";
        when(rest.getForObject(anyString(), eq(Object.class)))
                .thenReturn(mockResponse);

        Object result = service.getProducts();

        assertEquals(mockResponse, result);
    }

    @Test
    void testFallback() {
        Object result = service.fallback(new RuntimeException("down"));
        assertEquals("Product Service is currently unavailable. Please try again later.", result);
    }

    @Test
    void testBuyProduct() {
        String result = service.buyProduct(1);
        assertEquals("Purchase request sent for Product ID: 1", result);
    }
}
