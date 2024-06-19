package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.persistance.entities.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductId(Long productId);
}
