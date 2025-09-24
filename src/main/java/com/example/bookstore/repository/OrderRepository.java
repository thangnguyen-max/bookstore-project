package com.example.bookstore.repository;

import com.example.bookstore.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order save(Order order);

    List<Order> findAll();

    Order findById(long id);

    void deleteById(long id);

    List<Order> findByUserId(long id);
}
