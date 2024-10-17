package com.czavala.productmanagementsystem.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveCustomerDto implements Serializable {

    @NotBlank(message = "{generic.notblank}")
    private String firstname;

    @NotBlank(message = "{generic.notblank}")
    private String lastname;

    @NotBlank(message = "{generic.notblank}")
    @Size(min = 5, max = 15, message = "{generic.size}")
    private String username;
}
