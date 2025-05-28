package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserId(Long userId);
}
