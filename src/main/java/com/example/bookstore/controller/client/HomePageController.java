package com.example.bookstore.controller.client;

import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.CartDetails;
import com.example.bookstore.domain.Product;
import com.example.bookstore.domain.User;
import com.example.bookstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
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

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        Cart cart = this.productService.fetchByUser(currentUser) == null ? new Cart() : this.productService.fetchByUser(currentUser);
        List<CartDetails> cartDetails = cart.getCartDetails() == null ? new ArrayList<>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetails cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetails> cartDetails = cart == null ? new ArrayList<CartDetails>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @GetMapping("/shop")
    public String getShopPage(Model model) {
        List<Product> product = this.productService.getAllProducts();
        model.addAttribute("products", product);
        return "client/shop/show";
    }
}
