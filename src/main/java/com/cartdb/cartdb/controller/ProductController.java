package com.cartdb.cartdb.controller;

import com.cartdb.cartdb.model.Product;
import com.cartdb.cartdb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/getAll")
    ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/getAvailable")
    ResponseEntity<List<Product>> getAvailable(){
        return ResponseEntity.ok(productService.getAvailableProducts());
    }

    @PostMapping("/purchase")
    ResponseEntity<String> purchase(@RequestBody HashMap<String, Integer> body){
        if(productService.purchaseProduct(body.get("id"), body.get("quantity"))){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable long id ){
        if(productService.deleteProduct(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable long id){
        if(productService.updateProduct(id, product)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.addProduct(product));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Optional<Product> productOptional = productService.getProductById(id);

        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


}
