package com.czavala.productmanagementsystem.dto.order;

import com.czavala.productmanagementsystem.dto.cart.CartDto;
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
public class OrderDto implements Serializable {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("order_products")
    private List<OrderProductDto> orderProducts = new ArrayList<>();

    @JsonProperty("total_order")
    private Long totalOrder;

    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

    private CartDto cartDto;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
