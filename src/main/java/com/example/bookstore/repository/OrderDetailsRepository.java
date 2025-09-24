package com.example.bookstore.repository;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail save(OrderDetail orderDetail);

    List<OrderDetail> findByOrder(Order order);
}
