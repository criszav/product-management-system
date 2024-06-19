package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.order.OrderDto;
import com.czavala.productmanagementsystem.dto.order.OrderProductDto;
import com.czavala.productmanagementsystem.persistance.entities.order.Order;
import com.czavala.productmanagementsystem.persistance.entities.order.OrderProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderMapper {

    public OrderDto mapToOrderDto(Order order) {

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderProducts(mapToOrderProductDto(order.getOrderProducts()));
        orderDto.setTotalOrder(order.getTotalOrder());
        orderDto.setNumberOfProducts(order.getNumberOfProducts());
        orderDto.setCreatedAt(order.getCreatedAt());

        return orderDto;
    }

    public List<OrderProductDto> mapToOrderProductDto(List<OrderProduct> orderProducts) {

        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for (OrderProduct op : orderProducts) {

            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.setId(op.getId());
            orderProductDto.setProduct(op.getProduct());
            orderProductDto.setOrder(op.getOrder());
            orderProductDto.setQuantity(op.getQuantity());
            orderProductDto.setTotalPrice(op.getTotalPrice());

            orderProductDtos.add(orderProductDto);
        }
        return orderProductDtos;
    }


}
