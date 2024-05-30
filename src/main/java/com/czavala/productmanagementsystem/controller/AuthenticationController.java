package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.auth.AuthRequestDto;
import com.czavala.productmanagementsystem.dto.auth.AuthResponseDto;
import com.czavala.productmanagementsystem.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        AuthResponseDto authResponse = authenticationService.login(authRequestDto);
        return ResponseEntity.ok(authResponse);
    }

    // endpoint para visualizar la validez de un jwt
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }
}
