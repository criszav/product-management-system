package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.persistance.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
