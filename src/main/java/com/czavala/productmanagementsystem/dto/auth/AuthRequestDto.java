package com.czavala.productmanagementsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto implements Serializable {

    @NotBlank(message = "{generic.notblank}")
    private String username;
    @NotBlank(message = "{generic.notblank}")
    private String password;
}
