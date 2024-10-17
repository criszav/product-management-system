package com.czavala.productmanagementsystem.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "{generic.positive}")
    @JsonProperty("product_id")
    private Long productId;

    @Positive(message = "{generic.positive}")
    private Integer quantity;

}
