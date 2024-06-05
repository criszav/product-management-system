package com.czavala.productmanagementsystem.dto.customer;

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

    private String firstname;

    private String lastname;

    @Size(min = 5, message = "Username must contain at least 5 characters")
    private String username;
}
