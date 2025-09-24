package com.example.bookstore.controller.admin;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.Product;
import com.example.bookstore.domain.User;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.ProductService;
import com.example.bookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashboardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model){
        List<User> users = this.userService.getAllUser();
        List<Product> products = this.productService.getAllProducts();
        List<Order> orders = this.orderService.findAll();
        model.addAttribute("users", users.size());
        model.addAttribute("products", products.size());
        model.addAttribute("orders", orders.size());
        return "admin/dashboard/show";
    }
}
