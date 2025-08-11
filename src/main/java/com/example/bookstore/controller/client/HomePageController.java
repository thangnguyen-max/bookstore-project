package com.example.bookstore.controller.client;

import com.example.bookstore.domain.Product;
import com.example.bookstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> product = this.productService.getAllProducts();
        model.addAttribute("products", product);
        return "/client/homepage/show";
    }
}
