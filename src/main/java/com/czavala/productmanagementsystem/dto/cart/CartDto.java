package com.czavala.productmanagementsystem.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {

    private Long id;

    private List<CartItemDto> cartItems = new ArrayList<>();

    private Long totalCart;

    private Integer numberOfProducts;

    private Long userId;
}
