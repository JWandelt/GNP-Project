package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(path = "products/{productVendor}")
    public List<Product> getProductsByVendor(@PathVariable("productVendor") String productVendor) {
        return productService.getProductsByVendor(productVendor);
    }

    @GetMapping(path = "{productName}")
    public Product getProductByName(@PathVariable("productName") String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping(path = "{productName}")
    public void deleteProduct(@PathVariable("productName") String name) {
        productService.deleteProduct(name);
    }

    @PutMapping(path = "{productName}")
    public void updateProduct(@PathVariable("productName") String productName, @RequestParam(required = false) Integer quantity,
                              @RequestParam(required = false) Integer price) {
        productService.updateProduct(productName,quantity,price);
    }
}
