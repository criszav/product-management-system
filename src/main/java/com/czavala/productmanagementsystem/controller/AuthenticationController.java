package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.auth.LogoutResponse;
import com.czavala.productmanagementsystem.dto.auth.AuthRequestDto;
import com.czavala.productmanagementsystem.dto.auth.AuthResponseDto;
import com.czavala.productmanagementsystem.dto.customer.CustomerProfileDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        AuthResponseDto authResponse = authenticationService.login(authRequestDto);
        return ResponseEntity.ok(authResponse);
    }

    // endpoint para visualizar la validez de un jwt
    @PreAuthorize("permitAll")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileDto> findMyProfile() { // todo - retornar user dto en lugar de user entity
        CustomerProfileDto user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Successful logout.", LocalDateTime.now()));
    }
}
