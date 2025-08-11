package com.example.bookstore.controller.client;

import com.example.bookstore.domain.Product;
import com.example.bookstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable long id, Model model) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "client/product/product-detail";
    }
}
