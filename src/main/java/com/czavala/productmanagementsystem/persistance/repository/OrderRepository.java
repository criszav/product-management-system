package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.persistance.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
