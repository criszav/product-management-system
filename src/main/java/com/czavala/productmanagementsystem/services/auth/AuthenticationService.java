package com.czavala.productmanagementsystem.services.auth;

import com.czavala.productmanagementsystem.dto.auth.AuthRequestDto;
import com.czavala.productmanagementsystem.dto.auth.AuthResponseDto;
import com.czavala.productmanagementsystem.dto.auth.RegisteredUserDto;
import com.czavala.productmanagementsystem.dto.auth.SaveRegisterUserDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisteredUserDto registerCustomer(SaveRegisterUserDto saveRegisterUserDto) {

        User user = userService.registerOneCustomer(saveRegisterUserDto); // realiza el registro de un nuevo customer

        RegisteredUserDto userDto = new RegisteredUserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);

        return userDto;
    }

    // genera claims extras que son agregados al payload del token
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();

        // agrega nombre y apellido al payload del token
        extraClaims.put("name", user.getFirstname() + " " + user.getLastname());
        // agrega roles del user al payload del token
        extraClaims.put("role", user.getRole().name());
        // agrega authorities del user al payload del token
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) {

        // crea un authentication token para ser aautenticado
        // 'UsernamePasswordAuthenticationToken' es una implementacion de la interfaz 'Authentication' que contiene username y clave ingresados por el user
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequestDto.getUsername(),
                authRequestDto.getPassword()
        );

        // realiza la autenticacion del token creado previamente con el username y clave del user
        authenticationManager.authenticate(authenticationToken);

        // una vez autenticado el user, obtiene los datos de aquel user
        UserDetails user = userService.findByUsername(authRequestDto.getUsername()).get();

        // genera token con los datos del user (que seacaba de autenticar) obtenidos previamente
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        // crea el dto para enviar el jwt de vuelta del user recien autenticado
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setJwt(jwt);
        authResponseDto.setIssuedAt(LocalDateTime.now());

        return authResponseDto;
    }

    public boolean validateToken(String jwt) {

        /*
        Al intentar extraer un claim del jwt (username en este caso), NO podrá extrarse si el formato del token es incorrecto,
        si la firma del token es invalida o si el token ya expiró.

        Eso significa que para extraer un claim del jwt, se valida el formato del token, la firma de este
        y si aun es valido el token.
        */

        try {
            // si es posible extraer el username desde el jwt, entonces el token es valido (retorna true)
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            // si NO es posible extraer el username desde el jwt, entonces el token NO es valido (retorna false)
            System.out.println(e.getMessage());
            return false;
        }
    }
}
