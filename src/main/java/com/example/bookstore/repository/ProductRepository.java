package com.example.bookstore.repository;

import com.example.bookstore.domain.Product;
import com.example.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
