package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.persistance.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}
