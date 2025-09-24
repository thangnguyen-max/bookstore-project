package com.example.bookstore.controller.client;

import com.example.bookstore.domain.*;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.ProductService;
import com.example.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public ItemController(ProductService productService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable long id, Model model) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "/client/product/productDetails";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@ModelAttribute("product") Product product, @PathVariable("id") long id, HttpServletRequest request) {
        long productId = id;
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handleAddToCart(email, productId, session);
        return "redirect:/";
    }

    @PostMapping("/delete-product-to-cart/{id}")
    public String deleteProductToCart(@PathVariable("id") long id, HttpServletRequest request) {
        long cartDetailId = id;
        HttpSession session = request.getSession(false);
        this.productService.deleteCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        Cart cart = this.productService.fetchByUser(currentUser);
        List<CartDetails> cartDetails = cart == null ? new ArrayList<CartDetails>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetails cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        model.addAttribute("order", new Order());
        return "/client/cart/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request,
                                   @RequestParam("receiverName") String receiverName,
                                   @RequestParam("receiverPhone") String receiverPhone,
                                   @RequestParam("receiverAddress") String receiverAddress) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.productService.placeOrder(currentUser, session, receiverName, receiverPhone, receiverAddress);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String thanks() {
        return "/client/cart/thanks";
    }

    @GetMapping("/order-history")
    public String orderHistory(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long id = (long) session.getAttribute("id");
        List<Order> orders = this.orderService.findOrderByUserId(id);
        model.addAttribute("orders", orders);
        return "/client/orderHistory/show";
    }

    @GetMapping("/order-history/detail/{id}")
    public String orderHistoryDetail(@PathVariable("id") long id, Model model) {
        Order order = this.orderService.findById(id);
        int quantity = 0;
        for(OrderDetail orderDetail : order.getOrderDetails()) {
            quantity += (int) orderDetail.getQuantity();
        }
        model.addAttribute("quantity", quantity);
        model.addAttribute("order", order);
        return "/client/orderHistory/detail";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        Optional<User> user = this.userService.getUserById(id);
        user.ifPresent(value -> model.addAttribute("currentUser", value));
        return "/client/profile/show";
    }
}
