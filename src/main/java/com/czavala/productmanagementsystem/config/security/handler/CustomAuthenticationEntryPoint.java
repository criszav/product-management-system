package com.czavala.productmanagementsystem.config.security.handler;

import com.czavala.productmanagementsystem.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // se llama al metodo commence cuando un user intenta acceder a un recurso protegido, sin estar autenticado
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(authException.getLocalizedMessage());
        apiError.setMessage("Access denied. Must be authenticated to access this resource.");
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        // especifica que el tipo de respuesta será de tipo json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // respuesta sera de tipo 401 (solicitud NO autorizada)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // clase ObjectMapper permite leer y escribir json
        ObjectMapper objectMapper = new ObjectMapper();
        // agrega modulo que permite manejar horas y fechas en json
        objectMapper.registerModule(new JavaTimeModule());

        // pasa nuestro ApiError a formato json
        String apiErrorJson = objectMapper.writeValueAsString(apiError);

        response.getWriter().write(apiErrorJson);
    }
}
