package com.czavala.productmanagementsystem.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartRequest implements Serializable {

    private Long productId;
    private Integer quantity;

}
