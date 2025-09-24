package com.example.bookstore.repository;

import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.CartDetails;
import com.example.bookstore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {

    boolean existsByCartAndProduct(Cart cart , Product product);

    CartDetails findByCartAndProduct(Cart cart, Product product);

    Optional<CartDetails> findById(long id);

    void deleteById(long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartDetails cd WHERE cd.cart.id = :cartId")
    void deleteAllByCartId(@Param("cartId") Long cartId);

}
