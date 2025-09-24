package com.example.bookstore.controller.admin;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;
import com.example.bookstore.domain.User;
import com.example.bookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getAdminOrder(Model model) {
        List<Order> orders = this.orderService.findAll();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetail(@PathVariable("id") long id, Model model) {
        Order order = this.orderService.findById(id);
        List<OrderDetail> orderDetails = this.orderService.findByOrder(order);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        return "admin/order/orderDetails";
    }

    @GetMapping("/admin/order/update/{id}")
    public String updateOrder(@PathVariable("id") long id, Model model) {
        Order order = this.orderService.findById(id);
        model.addAttribute("order", order);
        return "admin/order/orderUpdate";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("order") Order order) {
        Order currentOrder = this.orderService.findById(order.getId());
        if (currentOrder != null) {
            currentOrder.setStatus(order.getStatus());
            this.orderService.save(currentOrder);
        }
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") long id, Model model) {
        model.addAttribute("order", this.orderService.findById(id));
        model.addAttribute("id", id);
        return "admin/order/orderDelete";
    }

    @PostMapping("/admin/order/delete")
    public String handleDeleteOrder(@ModelAttribute("order") Order order) {
        this.orderService.deleteOrderById(order.getId());
        return "redirect:/admin/order";
    }
}
