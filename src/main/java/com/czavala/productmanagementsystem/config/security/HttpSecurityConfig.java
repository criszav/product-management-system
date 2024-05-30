package com.czavala.productmanagementsystem.config.security;

import com.czavala.productmanagementsystem.config.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // deshabilita seguridad csrf (cross site request forgery)
                .csrf(csrf -> csrf.disable())

                // define tipo de aplicacion stateless
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                // indica que DaoAuthenticationProvider es la estrategia a utilizar para procesar la interfaz Authentication
                // en la clase ApplicationConfig definicamos la estrategia DaoAuthenticationProvider
                .authenticationProvider(daoAuthenticationProvider)

                // primero ejecuta nuestro filtro JwtAuthenticationFilter antes de ejecutar el filtro UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // manejo de endpoints publicos y privados
                .authorizeHttpRequests(authRequest -> {

                    // register y login son endpoints publicos (no es necesario estar autenticado para iniciar sesion o para registrarse)
                    authRequest.requestMatchers(HttpMethod.POST, "/customers").permitAll();
                    authRequest.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

                    // para todos los demas endpoints se debe estar autenticado
                    authRequest.anyRequest().authenticated();
                })

                .build();

    }
}
