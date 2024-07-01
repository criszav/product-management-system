package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.order.OrderDto;
import com.czavala.productmanagementsystem.persistance.entities.User;

import java.util.List;

public interface OrderService {

    OrderDto createOrderFromCartCheckout(String username);

    OrderDto getOrderById(User user, Long orderId);

    List<OrderDto> getOrdersByUser(User user);
}
