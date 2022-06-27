package com.jefferpgdev.hulkstore.service;

import com.jefferpgdev.hulkstore.model.Product;
import org.springframework.http.HttpStatus;

public interface ProductService {

    Iterable<Product> getProducts();

    Product getProduct(Integer id);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    HttpStatus deleteProduct(Integer id);

}
