package com.example.demo.controller;

import com.example.demo.exception.ResourceAlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void whenGetProductByName_thenReturnProduct() throws Exception{
        Product product = Product.builder()
                .name("smartphone")
                .price(1000)
                .vendor("XYZ")
                .quantity(400)
                .build();

        when(productService.getProductByName("smartphone")).thenReturn(product);

        mockMvc.perform(get("/api/product/smartphone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("smartphone"))
                .andExpect(jsonPath("$.price").value(1000));

        verify(productService,times(1)).getProductByName("smartphone");
    }

    @Test
    public void whenGetProductByName_thenNotFoundStatusCode() throws Exception{
        when(productService.getProductByName("smartphone")).thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/product/smartphone"))
                .andExpect(status().isNotFound());

        verify(productService,times(1)).getProductByName("smartphone");
    }

    @Test
    public void whenGetProductsByVendor_thenReturnProductList() throws Exception{
        Product product1 = Product.builder()
                .name("smartphone")
                .price(1000)
                .vendor("ABC")
                .quantity(400)
                .build();
        Product product2 = Product.builder()
                .name("hairbrush")
                .price(10)
                .vendor("ABC")
                .quantity(2000)
                .build();

        when(productService.getProductsByVendor("ABC")).thenReturn(List.of(product1,product2));

        mockMvc.perform(get("/api/product/products/ABC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("smartphone"))
                .andExpect(jsonPath("$[1].name").value("hairbrush"));

        verify(productService,times(1)).getProductsByVendor("ABC");
    }

    @Test
    public void whenGetProductsByVendor_thenReturnEmptyProductList() throws Exception{
        when(productService.getProductsByVendor("ABC")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/product/products/ABC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productService,times(1)).getProductsByVendor("ABC");
    }

    @Test
    public void whenGetProducts_thenReturnProductList() throws Exception{
        Product product1 = Product.builder()
                .name("smartphone")
                .price(1000)
                .vendor("XYZ")
                .quantity(400)
                .build();
        Product product2 = Product.builder()
                .name("hairbrush")
                .price(10)
                .vendor("ABC")
                .quantity(2000)
                .build();

        when(productService.getProducts()).thenReturn(List.of(product1,product2));

        mockMvc.perform(get("/api/product/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("smartphone"))
                .andExpect(jsonPath("$[1].name").value("hairbrush"));

        verify(productService,times(1)).getProducts();
    }

    @Test
    public void whenGetProducts_thenReturnEmptyProductList() throws Exception{
        when(productService.getProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/product/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productService,times(1)).getProducts();
    }

    @Test
    public void whenAddProduct_thenStatusOK() throws Exception{
        Product product = Product.builder()
                .name("hairbrush")
                .price(10)
                .vendor("ABC")
                .quantity(2000)
                .build();

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        verify(productService,times(1)).addProduct(product);
    }

    @Test
    public void whenAddProduct_thenStatusConflict() throws Exception{
        Product product = Product.builder()
                .name("hairbrush")
                .price(10)
                .vendor("ABC")
                .quantity(2000)
                .build();

        doThrow(new ResourceAlreadyExistException("Product with this name already exists"))
                .when(productService).addProduct(product);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isConflict());

        verify(productService,times(1)).addProduct(product);
    }

    @Test
    public void whenDeleteProduct_thenStatusOK() throws Exception{
        mockMvc.perform(delete("/api/product/notebook"))
                .andExpect(status().isOk());

        verify(productService,times(1)).deleteProduct("notebook");
    }

    @Test
    public void whenDeleteProduct_thenStatusNotFound() throws Exception{
        doThrow(new ResourceNotFoundException("Product not found"))
                .when(productService).deleteProduct("notebook");

        mockMvc.perform(delete("/api/product/notebook"))
                .andExpect(status().isNotFound());

        verify(productService,times(1)).deleteProduct("notebook");
    }

    @Test
    public void whenUpdateProduct_thenStatusOK() throws Exception{
        mockMvc.perform(put("/api/product/notebook")
                        .param("quantity","560")
                        .param("price","7"))
                .andExpect(status().isOk());

        verify(productService,times(1)).updateProduct("notebook",560,7);
    }

    @Test
    public void whenUpdateProduct_thenStatusNotFound() throws Exception{
        doThrow(new ResourceNotFoundException("Product not found"))
                .when(productService).updateProduct("notebook",560,7);

        mockMvc.perform(put("/api/product/notebook")
                        .param("quantity","560")
                        .param("price","7"))
                .andExpect(status().isNotFound());

        verify(productService,times(1)).updateProduct("notebook",560,7);
    }
}
