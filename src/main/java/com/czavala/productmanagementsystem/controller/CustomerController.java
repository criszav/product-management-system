package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.auth.RegisteredUserDto;
import com.czavala.productmanagementsystem.dto.auth.SaveRegisterUserDto;
import com.czavala.productmanagementsystem.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDto> registerCustomer(@RequestBody @Valid SaveRegisterUserDto saveRegisterUserDto) {
        RegisteredUserDto user = authenticationService.registerCustomer(saveRegisterUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
