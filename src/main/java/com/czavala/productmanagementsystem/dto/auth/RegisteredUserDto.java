package com.czavala.productmanagementsystem.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserDto implements Serializable {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String role;
    private String jwt;
}
