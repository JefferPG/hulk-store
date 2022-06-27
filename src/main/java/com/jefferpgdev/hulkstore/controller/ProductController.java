package com.jefferpgdev.hulkstore.controller;

import com.jefferpgdev.hulkstore.implement.ProductServiceImpl;
import com.jefferpgdev.hulkstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<Iterable<Product>> getProduct() {
        return ResponseEntity.ok(productServiceImpl.getProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productServiceImpl.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productServiceImpl.saveProduct(product));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productServiceImpl.updateProduct(product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer id) {
        return ResponseEntity.status(productServiceImpl.deleteProduct(id)).build();
    }
}
