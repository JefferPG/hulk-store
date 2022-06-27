package com.jefferpgdev.hulkstore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jefferpgdev.hulkstore.model.Product;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DataUtil {

    protected static List<Product> getProductList() {
        return Arrays.asList(
                Product.builder()
                        .id(1)
                        .code("TS000001")
                        .name("Camisa Blanca - Stranger Things")
                        .description("Camisa Blanca - Stranger Things")
                        .quantity(0)
                        .price(0.0)
                        .build(),
                Product.builder()
                        .id(2)
                        .code("TS000002")
                        .name("Camisa Negra - Batman")
                        .description("Camisa Negra - Batman")
                        .quantity(0)
                        .price(0.0)
                        .build()
        );
    }

    protected static Stream<Arguments> getProductListParameters() {
        return Stream.of(Arguments.of(getProductList()));
    }

    protected static Stream<Arguments> getProductParameters() {
        return getProductList().stream().map(x -> Arguments.arguments(x.getId(), x));
    }

    protected String getJsonFromObject(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T getObjectFromJson(String json, Class<T> getClass) {
        try {
            return new ObjectMapper().readValue(json, getClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getObjectListFromJson(String str, Class<? extends Collection> type, Class<T> elementType) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, mapper.getTypeFactory().constructCollectionType(type, elementType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
