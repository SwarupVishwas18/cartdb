package com.cartdb.cartdb.service;

import com.cartdb.cartdb.model.Product;
import com.cartdb.cartdb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private  final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")

    public Product addProduct(Product product){
        return productRepository.save(product);
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }



    @PreAuthorize("hasRole('USER')")
    public boolean purchaseProduct(long id, int qty){
        Optional<Product> productOpt = productRepository.findById((int) id);

        if(productOpt.isPresent()){
            Product product = productOpt.get();
            if(qty <= product.getQuantityAvailable()){
                product.setQuantityAvailable(product.getQuantityAvailable()-qty);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(long id, Product product){
        Optional<Product> productOpt = productRepository.findById((int) id);

        if(productOpt.isPresent()){
            Product oldProduct = productOpt.get();
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            productRepository.save(oldProduct);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(long id){
        Optional<Product> productOpt = productRepository.findById((int) id);

        if(productOpt.isPresent()){
            Product product = productOpt.get();
            productRepository.delete(product);
            return true;
        }
        return false;

    }

    public List<Product> getAvailableProducts(){
        return productRepository.getAvailableProducts();
    }

    public Optional<Product> getProductById(int id){
        return productRepository.findById(id);
    }
}
