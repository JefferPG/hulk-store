package com.jefferpgdev.hulkstore.implement;

import com.jefferpgdev.hulkstore.model.Product;
import com.jefferpgdev.hulkstore.repository.ProductRepository;
import com.jefferpgdev.hulkstore.service.ProductService;
import com.jefferpgdev.hulkstore.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private AppUtil appUtil;
    @Autowired private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Integer id) {
        appUtil.validateParameter(id, "id is required.");
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseGet(Product::new);
    }

    @Override
    public Product saveProduct(Product product) {
        appUtil.validateParameter(product.getCode(), "code is required.");
        appUtil.validateParameter(product.getName(), "name is required.");

        // Set default values quantity and price for new products
        product.setQuantity(0);
        product.setPrice(0.0);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        appUtil.validateParameter(product.getId(), "id is required.");
        appUtil.validateParameter(product.getCode(), "code is required.");
        appUtil.validateParameter(product.getName(), "name is required.");
        appUtil.validateParameter(product.getQuantity(), "quantity is required.");
        appUtil.validateParameter(product.getPrice(), "price is required.");
        return productRepository.save(product);
    }

    @Override
    public HttpStatus deleteProduct(Integer id) {
        appUtil.validateParameter(id, "id is required.");
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    /**
     * Description: Calculate the average price and update quantity and price into Products
     * */
    public void updateProductPrice(Integer id, Integer quantity, Double price) {
        appUtil.validateParameter(id, "id product is required.");
        appUtil.validateParameter(quantity, "quantity is required.");
        appUtil.validateParameter(price, "price is required.");

        Product product = getProduct(id);
        product.setQuantity(quantity);
        product.setPrice(price);

        productRepository.save(product);
    }

}
