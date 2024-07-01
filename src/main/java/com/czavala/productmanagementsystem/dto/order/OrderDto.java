package com.czavala.productmanagementsystem.dto.order;

import com.czavala.productmanagementsystem.dto.cart.CartDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long userId;
    private List<OrderProductDto> orderProducts = new ArrayList<>();
    private Long totalOrder;
    private Integer numberOfProducts;
    private CartDto cartDto;
    private String orderStatus;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}
