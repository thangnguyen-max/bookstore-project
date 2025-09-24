package com.example.bookstore.repository;

import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.Product;
import com.example.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);

    Optional<Cart> findById(Long id);

    void deleteByUser(User user);

    Cart save(Cart cart);

    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :id")
    void deleteCartById(@Param("id") Long id);

}
