package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.order.OrderDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(user, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/current")
    public ResponseEntity<List<OrderDto>> getUserOrders(@AuthenticationPrincipal User user) {
        List<OrderDto> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }
}
