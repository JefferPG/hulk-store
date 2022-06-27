package com.jefferpgdev.hulkstore.repository;

import com.jefferpgdev.hulkstore.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    @Test
    void testFindAllProducts() {
        List<Product> productList = productRepository.findAll();

        assertEquals(2, productList.size());
    }

    @ParameterizedTest(name = "{index} => id {0} should be get product this data: code {1}, name {2}, description {3}, quantity {4}, price {5}")
    @CsvSource(value = {
            "1, TS000001, Camisa Blanca - Stranger Things, Camisa Blanca - Stranger Things, 0, 0.0",
            "2, TS000002, Camisa Negra - Batman, null, 0, 0.0"
    }, nullValues = "null")
    void testFindProductById(int id, String code, String name, String description, int quantity, double price) {
        Product product = Product.builder().id(id).code(code).name(name).description(description).quantity(quantity).price(price).build();

        Optional<Product> getProduct = productRepository.findById(id);

        assertEquals(product, getProduct.get());
    }

    @ParameterizedTest(name = "{index} => Inserting product whit this data: code {1}, name {2}, description {3}, quantity {4}, price {5}, should return the new id {0}")
    @CsvSource(value = {
            "3, TS000003, Camisa Negra - Superman, null, 0, 0.0"
    }, nullValues = "null")
    void testSaveProduct(int id, String code, String name, String description, int quantity, double price) {
        Product newProduct = Product.builder().code(code).name(name).description(description).quantity(quantity).price(price).build();

        Product savedProduct = productRepository.save(newProduct);

        assertEquals(id, savedProduct.getId());
    }

    @ParameterizedTest(name = "{index} => Updating product whit this data: code {1}, name {2}, description {3}, quantity {4}, price {5}, should return this updated data description {3}, quantity {4} and price {5}")
    @CsvSource(value = {
            "2, TS000002, Camisa Negra - Batman, Description updated, 10, 2.0"
    }, nullValues = "null")
    void testUpdateProductById(int id, String code, String name, String description, int quantity, double price) {
        Product updateProduct = Product.builder().id(id).code(code).name(name).description(description).quantity(quantity).price(price).build();

        Product updatedProduct = productRepository.save(updateProduct);

        assertEquals(description, updatedProduct.getDescription());
        assertEquals(quantity, updatedProduct.getQuantity());
        assertEquals(price, updatedProduct.getPrice());
    }

    @ParameterizedTest(name = "{index} => Deleting product whit this id: {0}, before looking for it whit findById")
    @CsvSource(value = {
            "1", "2"
    }, nullValues = "null")
    void testDeleteProductById(int id) {
        productRepository.deleteById(id);

        Optional<Product> getProduct = productRepository.findById(id);

        assertFalse(getProduct.isPresent());
    }

}