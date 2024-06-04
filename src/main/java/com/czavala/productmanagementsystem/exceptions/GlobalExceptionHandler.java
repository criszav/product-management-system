package com.czavala.productmanagementsystem.exceptions;

import com.czavala.productmanagementsystem.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice // mapea, controla excepciones
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // mapea errores genericos
    public ResponseEntity<ApiError> genericExceptionHandler(Exception e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage("Internal error. Please contact the admin.");
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // mapea errores de validacion de argumentos ingresados por el usuario
    public ResponseEntity<ApiError> handlerMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        // los Set no permiten datos duplicados, de esa forma no duplicamos un error
        Set<String> validationErrors = new HashSet<>();

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        e.getBindingResult().getAllErrors().stream() // obtiene todos los errores de la exception
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage(); // extrae el mensaje de la exception
                    validationErrors.add(errorMessage); // agrega mensaje de error al Set de errores de validacion
                });
        apiError.setValidationErrors(validationErrors); // asigna el Set al dto 'ApiError'

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage("Username or password incorrect");
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage(e.getMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


}
