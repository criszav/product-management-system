package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.customer.CustomerDto;
import com.czavala.productmanagementsystem.dto.auth.RegisteredUserDto;
import com.czavala.productmanagementsystem.dto.auth.SaveRegisterUserDto;
import com.czavala.productmanagementsystem.dto.customer.SaveCustomerDto;
import com.czavala.productmanagementsystem.services.UserService;
import com.czavala.productmanagementsystem.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    // Crea un nuevo usuario
    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDto> registerCustomer(@RequestBody @Valid SaveRegisterUserDto saveRegisterUserDto) {
        RegisteredUserDto user = authenticationService.registerCustomer(saveRegisterUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Actualiza datos de un usuario existente
    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody @Valid SaveCustomerDto saveCustomerDto) {
        CustomerDto customerDto = userService.updateCustomer(id, saveCustomerDto);
        return ResponseEntity.ok(customerDto);
    }
}
