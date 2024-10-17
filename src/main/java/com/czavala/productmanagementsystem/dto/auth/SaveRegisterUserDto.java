package com.czavala.productmanagementsystem.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveRegisterUserDto implements Serializable {

    @NotBlank(message = "{generic.notblank}")
    private String firstname;

    @NotBlank(message = "{generic.notblank}")
    private String lastname;

    @NotBlank(message = "{generic.notblank}")
    @Size(min = 5, max = 15, message = "{generic.size}")
    private String username;

    @NotBlank(message = "{generic.notblank}")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{generic.mail}")
    private String email;

    @NotBlank(message = "{generic.notblank}")
    @Size(min = 12, max = 255, message = "{generic.size}")
    private String password;

    @NotBlank(message = "{generic.notblank}")
    @Size(min = 12, max = 255, message = "{generic.size}")
    @JsonProperty("repeated_password")
    private String repeatedPassword;
}
