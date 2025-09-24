package com.example.bookstore.service;

import com.example.bookstore.domain.*;
import com.example.bookstore.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public ProductService(ProductRepository productRepository,
                          CartRepository cartRepository,
                          CartDetailsRepository cartDetailsRepository,
                          UserService userService, OrderRepository orderRepository,
                          OrderDetailsRepository orderDetailsRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void addProduct(Product product) {
        this.productRepository.save(product);
    }

    public Optional<Product> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddToCart(String email, long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = cartRepository.findByUser(user);
            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                CartDetails oldDetails = this.cartDetailsRepository.findByCartAndProduct(cart, product);

                if (oldDetails == null) {
                    CartDetails cartDetails = new CartDetails();
                    cartDetails.setCart(cart);
                    cartDetails.setProduct(product);
                    cartDetails.setPrice(product.getPrice());
                    cartDetails.setQuantity(1);
                    this.cartDetailsRepository.save(cartDetails);
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetails.setQuantity(oldDetails.getQuantity() + 1);
                    this.cartDetailsRepository.save(oldDetails);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void deleteCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetails> cartDetailOptional = this.cartDetailsRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetails cartDetail = cartDetailOptional.get();
            Cart cart = cartDetail.getCart();
            this.cartDetailsRepository.deleteById(cartDetailId);
            if (cart.getSum() > 1) {
                int sum = cart.getSum() - 1;
                cart.setSum(sum);
                session.setAttribute("sum", sum);
                this.cartRepository.save(cart);
            } else {
                cart.setSum(0);
                this.cartRepository.save(cart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetails> cartDetails) {
        for (CartDetails cartDetail : cartDetails) {
            Optional<CartDetails> cartDetailsOptional = this.cartDetailsRepository.findById(cartDetail.getId());
            if (cartDetailsOptional.isPresent()) {
                CartDetails currentCartDetail = cartDetailsOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailsRepository.save(currentCartDetail);
            }
        }
    }


    public void placeOrder(User user, HttpSession session,
                           String receiverName,
                           String receiverPhone,
                           String receiverAddress) {
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetails> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverPhone(receiverPhone);
                order.setReceiverAddress(receiverAddress);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetails cd : cartDetails) {
                    Optional<Product> currentProduct =  this.productRepository.findById(cd.getProduct().getId());
                    if(currentProduct.isPresent()) {
                        Product product = currentProduct.get();
                        product.setQuantity((int) (cd.getProduct().getQuantity() - cd.getQuantity()));
                        product.setSold((int) (cd.getProduct().getSold() + cd.getQuantity()));
                        this.productRepository.save(product);
                    }
                    sum += (cd.getPrice() * cd.getQuantity());
                }
                order.setTotalPrice(sum);
                this.orderRepository.save(order);
                for (CartDetails cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetail.setPrice(cartDetail.getPrice());
                    this.orderDetailsRepository.save(orderDetail);
                }



                this.cartDetailsRepository.deleteAllByCartId(cart.getId());
                this.cartRepository.deleteCartById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }
}
