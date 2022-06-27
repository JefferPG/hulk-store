package com.jefferpgdev.hulkstore.controller;

import com.jefferpgdev.hulkstore.implement.ProductServiceImpl;
import com.jefferpgdev.hulkstore.model.Product;
import com.jefferpgdev.hulkstore.utils.DataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends DataUtil{

    private final String URL = "/api/v1/product/";

    @Autowired private MockMvc mockMvc;
    @MockBean private ProductServiceImpl productServiceImpl;

    @ParameterizedTest(name = "{index} => should get all products")
    @MethodSource("getProductListParameters")
    void testGetAllProducts(List<Product> productList) throws Exception {
        Mockito.when(productServiceImpl.getProducts()).thenReturn(productList);

        String result = mockMvc
                .perform(get(URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> productResponseList = getObjectListFromJson(result, List.class, Product.class);

        for (Product productResponse : productResponseList)
            assertEquals(
                    productList.stream().filter(x -> Objects.equals(x.getId(), productResponse.getId())).findFirst().orElse(new Product()),
                    productResponse
            );
    }

    @ParameterizedTest(name = "{index} => should get the product with id {0}")
    @MethodSource("getProductParameters")
    void testGetProduct(Integer id, Product product) throws Exception {
        Mockito.when(productServiceImpl.getProduct(id)).thenReturn(product);

        String result = mockMvc
                .perform(get(URL + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product productResponse = getObjectFromJson(result, Product.class);

        assertEquals(product, productResponse);
    }

    @Test
    void saveProduct() throws Exception {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}