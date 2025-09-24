package com.example.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name="products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, message = "Product name must be at least 2 characters")
    @NotEmpty(message = "Name must not be null")
    private String name;

    @NotNull
    @PositiveOrZero(message = "Price must be zero or positive")
    private double price;

    @NotNull
    @Min(value = 0, message = "Quantity must be zero or more")
    private int quantity;

    private String image;

    @NotNull
    @NotEmpty(message = "Description must not be null")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull
    @NotEmpty(message = "Author must not be null")
    private String author;

    @NotNull
    @PositiveOrZero(message = "Sold count must be zero or positive")
    private long sold;

    @NotNull
    @NotEmpty(message = "Category must not be null")
    private String category;

}