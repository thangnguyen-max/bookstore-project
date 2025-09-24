package com.example.bookstore.service;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;
import com.example.bookstore.domain.User;
import com.example.bookstore.repository.OrderDetailsRepository;
import com.example.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    public Order findById(long id) {
        return this.orderRepository.findById(id);
    }

    public List<OrderDetail> findByOrder(Order order) {
        return this.orderDetailsRepository.findByOrder(order);
    }

    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        this.orderRepository.deleteById(id);
    }

    public List<Order> findOrderByUserId(long id) {
        return this.orderRepository.findByUserId(id);
    }
}
