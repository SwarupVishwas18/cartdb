package com.cartdb.cartdb.service;

import com.cartdb.cartdb.model.Product;
import com.cartdb.cartdb.repository.ProductRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        product = new Product("soap", 100.00, 4);
    }

    @Test
    void testAddProduct(){
        when(productRepository.save(product)).thenReturn(product);
        Product saved = productService.addProduct(product);
        assertEquals("soap", saved.getName());
    }


    @Test
    void testGetAllProducts(){
        when(productRepository.findAll()).thenReturn(List.of(product));
        int size = productService.getAllProducts().size();
        assertEquals(1, size);
    }

    @Test
    void testGetProductById(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        Optional<Product> productOptional = productService.getProductById(anyInt());
        assertEquals("soap", productOptional.get().getName());
    }

    @Test
    void testGetAvailableProducts(){
        when(productRepository.getAvailableProducts()).thenReturn(List.of(product));
        int size = productService.getAvailableProducts().size();
        assertEquals(1, size);
    }

    @Test
    void testPurchaseAvailableQty(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        boolean purchased = productService.purchaseProduct(anyInt(), 2);
        assertTrue(purchased);
    }

    @Test
    void testPurchaseNotAvailableQty(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        boolean purchased = productService.purchaseProduct(anyInt(), 6);
        assertFalse(purchased);
    }

    @Test
    void testUpdateSuccess(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        boolean updated = productService.updateProduct(anyInt(), new Product("bleach",12.00,2));
        assertTrue(updated);
    }

    @Test
    void testUpdateFailure(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        boolean updated = productService.updateProduct(anyInt(), new Product("bleach",12.00,2));
        assertFalse(updated);
    }

    @Test
    void testDeleteSuccess(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        boolean deleted = productService.deleteProduct(anyInt());
        assertTrue(deleted);
    }

    @Test
    void testDeleteFailure(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        boolean deleted = productService.deleteProduct(anyInt());
        assertFalse(deleted);
    }




}
