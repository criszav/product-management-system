package com.czavala.productmanagementsystem.config.security.handler;

import com.czavala.productmanagementsystem.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(accessDeniedException.getLocalizedMessage());
        apiError.setMessage("Access denied. You do not have the required permissions to access this resource.");
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        // indica que la respuesta sera de tipo json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // indica que respuesta sera de tipo 403
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // objeto ObjectMapper permite leer y escribir json (es el tipo de respuesta especificado previamente)
        ObjectMapper objectMapper = new ObjectMapper();
        // agrega modulo que permite manejar horas y fechas en json
        objectMapper.registerModule(new JavaTimeModule());

        // pasa nuestro ApiError a formato json
        String apiErrorJson = objectMapper.writeValueAsString(apiError);

        response.getWriter().write(apiErrorJson);

    }
}
