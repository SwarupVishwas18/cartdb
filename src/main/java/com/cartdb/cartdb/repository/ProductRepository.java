package com.cartdb.cartdb.repository;

import com.cartdb.cartdb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product_tb WHERE quantity_available > 0", nativeQuery = true)
    public List<Product> getAvailableProducts();
}
