package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    public Product(String name, Integer quantity, Integer price, String vendor) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.vendor = vendor;
    }

    public Product(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private long id;
    @Column(unique = true)
    private String name;
    private Integer quantity;
    private Integer price;
    private String vendor;
}
