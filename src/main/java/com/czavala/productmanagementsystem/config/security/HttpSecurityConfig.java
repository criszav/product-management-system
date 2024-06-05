package com.czavala.productmanagementsystem.config.security;

import com.czavala.productmanagementsystem.config.security.filters.JwtAuthenticationFilter;
import com.czavala.productmanagementsystem.persistance.Utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

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
                    requestMatchers(authRequest);
                })

                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })

                .build();

    }

    private static void requestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        /* config endpoints de products */
        authRequest.requestMatchers(HttpMethod.GET, "/products/{id}")
                        .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name(), Role.CUSTOMER.name());
        authRequest.requestMatchers(HttpMethod.GET, "/find/{productName}")
                        .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name()); // todo - agregar role CUSTOMER
        authRequest.requestMatchers(HttpMethod.GET, "/products").permitAll();

        authRequest.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(Role.ADMIN.name());

        authRequest.requestMatchers(HttpMethod.PUT, "/products/{id}")
                .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name());
        authRequest.requestMatchers(HttpMethod.PUT, "/products/{id}/disable")
                .hasRole(Role.ADMIN.name());


        /* config endpoints de categories */
        authRequest.requestMatchers(HttpMethod.GET, "/categories/{id}")
                .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name(), Role.CUSTOMER.name());

        authRequest.requestMatchers(HttpMethod.GET, "/categories").permitAll();

        authRequest.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(Role.ADMIN.name());

        authRequest.requestMatchers(HttpMethod.PUT, "/categories/{id}")
                .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name());
        authRequest.requestMatchers(HttpMethod.PUT, "/categories/{id}/disable")
                .hasRole(Role.ADMIN.name());


        /* config 'auth' endpoints */
        authRequest.requestMatchers(HttpMethod.PUT, "/customers/{id}")
                        .hasAnyRole(Role.ADMIN.name(), Role.ASSISTANT_ADMIN.name());
        authRequest.requestMatchers(HttpMethod.POST, "/customers/register").permitAll();
        authRequest.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authRequest.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();

        // para todos los demas endpoints se debe estar autenticado
        authRequest.anyRequest().authenticated();
    }
}
