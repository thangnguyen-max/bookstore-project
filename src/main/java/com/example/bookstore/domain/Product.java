package com.example.bookstore.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="products")
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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", sold=" + sold +
                ", category='" + category + '\'' +
                '}';
    }
}