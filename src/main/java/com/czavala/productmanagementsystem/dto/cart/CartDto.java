package com.czavala.productmanagementsystem.dto.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {

    private Long id;

    @JsonProperty("cart_items")
    private List<CartItemDto> cartItems = new ArrayList<>();

    @JsonProperty("total_cart")
    private Long totalCart;

    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("cart_status")
    private String cartStatus;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("last_modified")
    private LocalDateTime lastModified;
}
