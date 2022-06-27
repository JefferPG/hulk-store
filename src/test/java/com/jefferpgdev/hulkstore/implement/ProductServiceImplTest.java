package com.jefferpgdev.hulkstore.implement;

import com.jefferpgdev.hulkstore.model.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    @Autowired private ProductServiceImpl productServiceImpl;

    @Order(1)
    @Test
    void testGetAllProducts() {
        // Execute
        List<Product> productList = productServiceImpl.getProducts();

        // Assert
        assertEquals(2, productList.size());
    }

    @Order(2)
    @ParameterizedTest(name = "{index} => id {0} should be get product this data: code {1}, name {2}, description {3}, quantity {4}, price {5}")
    @CsvSource(value = {
            "1, TS000001, Camisa Blanca - Stranger Things, Camisa Blanca - Stranger Things, 0, 0.0",
            "2, TS000002, Camisa Negra - Batman, null, 0, 0.0"
    }, nullValues = "null")
    void testGetAProductById(int id, String code, String name, String description, int quantity, double price) {
        Product product = Product.builder().id(id).code(code).name(name).description(description).quantity(quantity).price(price).build();

        // Execute
        Product getProduct = productServiceImpl.getProduct(id);

        // Assert
        assertEquals(product, getProduct);
    }

    @Order(3)
    @ParameterizedTest(name = "{index} => Inserting product whit this data: code {1}, name {2}, description {3}, quantity {4}, price {5}, should return the new id {0}")
    @CsvSource(value = {
            "3, TS000003, Camisa Negra - Superman, null, 0, 0.0"
    }, nullValues = "null")
    void testSaveANewProduct(int id, String code, String name, String description, int quantity, double price) {
        Product newProduct = Product.builder().code(code).name(name).description(description).quantity(quantity).price(price).build();

        // Execute
        Product savedProduct = productServiceImpl.saveProduct(newProduct);

        // Assert
        assertEquals(id, savedProduct.getId());
    }

    @Order(4)
    @ParameterizedTest(name = "{index} => Updating product whit this data: code {1}, name {2}, description {3}, quantity {4}, price {5}, should return this updated data description {3}, quantity {4} and price {5}")
    @CsvSource(value = {
            "2, TS000002, Camisa Negra - Batman, Description updated, 10, 2.0"
    }, nullValues = "null")
    void testUpdateProductById(int id, String code, String name, String description, int quantity, double price) {
        Product updateProduct = Product.builder().id(id).code(code).name(name).description(description).quantity(quantity).price(price).build();

        // Execute
        Product updatedProduct = productServiceImpl.updateProduct(updateProduct);

        // Assert
        assertEquals(description, updatedProduct.getDescription());
        assertEquals(quantity, updatedProduct.getQuantity());
        assertEquals(price, updatedProduct.getPrice());
    }

    @Order(5)
    @ParameterizedTest(name = "{index} => Deleting product whit this id: {0}, before looking for it whit findById")
    @CsvSource(value = {
            "2,true", "3,true", "4,false"
    }, nullValues = "null")
    void testDeleteProductById(int id, boolean result) {
        // Execute
        HttpStatus httpStatus = productServiceImpl.deleteProduct(id);

        Product getProduct = productServiceImpl.getProduct(id);

        // Assert
        if (result)
            assertEquals(HttpStatus.OK, httpStatus);
        else
            assertEquals(HttpStatus.NOT_FOUND, httpStatus);
    }

    @Order(6)
    @ParameterizedTest(name = "{index} => Updating product quantity and product price whit this data: id {0}, quantity {1}, price {2}")
    @CsvSource(value = {
            "1, 5, 5.0",
            "1, 20, 6.0",
            "1, 25, 5.8",
            "1, 5, 5.0",
            "1, 1, 5.0"
    }, nullValues = "null")
    void testUpdateProductPriceAndQuantityByIdWhitSuccess(int id, int quantity, double price) {
        // Execute
        productServiceImpl.updateProductPrice(id, quantity, price);

        Product getProduct = productServiceImpl.getProduct(id);

        // Assert
        assertEquals(quantity, getProduct.getQuantity());
        assertEquals(price, getProduct.getPrice());
    }
}