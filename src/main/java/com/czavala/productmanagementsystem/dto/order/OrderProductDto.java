package com.czavala.productmanagementsystem.dto.order;

import com.czavala.productmanagementsystem.persistance.entities.Product;
import com.czavala.productmanagementsystem.persistance.entities.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto implements Serializable {

    private Long id;
//    private Order order;
    private Product product;
    private Integer quantity;
    private Long totalPrice;
}
