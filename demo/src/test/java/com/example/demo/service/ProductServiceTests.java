package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void givenProduct_whenAddNewProduct_returnProduct(){
        Product product = Product.builder()
                .name("Gel")
                .quantity(100)
                .price(10)
                .vendor("P&G")
                .build();

        when(productRepository.findProductByName("Gel")).thenReturn(Optional.empty());

        productService.addProduct(product);

        verify(productRepository,times(1)).save(product);

    }

    @Test
    public void givenName_whenDeleteProduct_deleteProduct(){
        when(productRepository.findProductByName("Gel")).thenReturn(Optional.of(new Product(1L)));

        productService.deleteProduct("Gel");

        verify(productRepository, times(1)).deleteById(1L);

    }

    @Test
    public void givenIdAndName_whenUpdateProduct_updateProductData(){
        Product product = Product.builder()
                .id(1L)
                .name("Gel")
                .quantity(100)
                .price(10)
                .vendor("P&G")
                .build();

        when(productRepository.findProductByName("Gel")).thenReturn(Optional.of(product));

        productService.updateProduct("Gel",50,1);

        Assertions.assertEquals(50, product.getQuantity());
        Assertions.assertEquals(1, product.getPrice());
    }

}
