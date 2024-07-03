package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product("Toothbrush", 100, 5, "P&G");
            Product product2 = new Product("Floss", 50, 15, "DentMed");

            productRepository.saveAll(List.of(product1, product2));
        };
    }
}
