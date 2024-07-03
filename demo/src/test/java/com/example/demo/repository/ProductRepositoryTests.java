package com.example.demo.repository;

import com.example.demo.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenProduct_whenSave_thenReturnSavedProduct(){
        Product product = Product.builder()
                .name("Gel")
                .quantity(100)
                .price(10)
                .vendor("P&G")
                .build();

        Assertions.assertEquals(product, productRepository.save(product));
    }

    @Test
    public void givenProductsList_whenSaveAll_thenReturnSavedProducts(){
        Product product = Product.builder()
                .name("Gel")
                .quantity(100)
                .price(10)
                .vendor("P&G")
                .build();
        Product product2 = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        List<Product> productToSave = List.of(product, product2);

        Assertions.assertEquals(productToSave, productRepository.saveAll(productToSave));
    }

    @Test
    public void givenIdAndProduct_whenExistsById_thenReturnTrue(){
        Product product = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        productRepository.save(product);

        List<Product> products = productRepository.findAll();
        System.out.println(products);
        boolean isProductAvailable = productRepository.existsById(1L);

        Assertions.assertTrue(isProductAvailable);
    }

    @Test
    public void givenProduct_whenFindById_thenReturnProduct(){
        Product product = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(1L);

        Assertions.assertTrue(foundProduct.isPresent());
    }

    @Test
    public void givenProductsList_whenFindAll_thenReturnProductsList(){
        Product product1 = Product.builder()
                .name("Gel")
                .quantity(100)
                .price(10)
                .vendor("P&G")
                .build();
        Product product2 = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        productRepository.saveAll(List.of(product1, product2));

        Assertions.assertEquals(2, productRepository.findAll().size());
    }

    @Test
    public void givenProduct_whenFindProductByEmail_thenReturnProduct(){
        final String name = "Toothpaste";

        Product product = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findProductByName(name);

        Assertions.assertTrue(foundProduct.isPresent());
    }

    @Test
    public void givenProductAndId_whenDeleteById_thenDeleteProduct(){
        Product product = Product.builder()
                .name("Toothpaste")
                .quantity(200)
                .price(5)
                .vendor("P&G")
                .build();

        productRepository.save(product);

        Assertions.assertTrue(productRepository.findById(1L).isPresent());

       productRepository.deleteById(1L);

        Assertions.assertTrue(productRepository.findById(1L).isEmpty());
    }
}
