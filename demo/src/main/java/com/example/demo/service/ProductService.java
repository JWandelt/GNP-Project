package com.example.demo.service;


import com.example.demo.exception.ResourceAlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByVendor(String vendor) {
        return productRepository.findProductsByVendor(vendor);
    }

    public Product getProductByName(String name) {
        Optional<Product> productOptional = productRepository.findProductByName(name);

        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        return productOptional.get();
    }

    public void addProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByName(product.getName());

        if (productOptional.isPresent()) {
            throw new ResourceAlreadyExistException("Product with this name already exists");
        }

        productRepository.save(product);
    }

    public void deleteProduct(String name) {
        Optional<Product> productOptional = productRepository.findProductByName(name);

        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Long id = productOptional.get().getId();

        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(String name, Integer quantity, Integer price) {
        Product product = productRepository.findProductByName(name)
                .orElseThrow(() ->  new ResourceNotFoundException("Product not found"));

        if (quantity != null && !Objects.equals(product.getQuantity(), quantity)) {
            product.setQuantity(quantity);
        }

        if (price != null && !Objects.equals(product.getPrice(), price)) {
            product.setPrice(price);
        }
    }
}
