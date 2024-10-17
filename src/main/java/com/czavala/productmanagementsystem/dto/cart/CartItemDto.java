package com.czavala.productmanagementsystem.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {

    private Long id;
    @JsonProperty("product_id") private Long productId;
    private Integer quantity;
    @JsonProperty("total_price") private Long totalPrice;
    private Long cartId;

}
