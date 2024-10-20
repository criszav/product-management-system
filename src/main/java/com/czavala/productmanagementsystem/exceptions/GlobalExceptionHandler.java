package com.czavala.productmanagementsystem.exceptions;

import com.cloudinary.Api;
import com.czavala.productmanagementsystem.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestControllerAdvice // mapea, controla excepciones
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // mapea errores de validacion de argumentos ingresados por el usuario
    public ResponseEntity<ApiError> handlerMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        // los Set no permiten datos duplicados, de esa forma no duplicamos un error
//        Set<String> validationErrors = new HashSet<>();
//        e.getBindingResult().getAllErrors() // obtiene todos los errores de la exception
//                .forEach(error -> {
//                    var errorMessage = error.getDefaultMessage(); // extrae el mensaje de la exception
//                    validationErrors.add(errorMessage); // agrega mensaje de error al Set de errores de validacion
//                });

        List<ObjectError> errors = e.getAllErrors();
        List<String> details = errors.stream().map(error -> {

            if (error instanceof FieldError fieldError) {
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            }

            return error.getDefaultMessage();
        }).toList();

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getMessage());
        apiError.setMessage("The request contains invalid or incomplete parameters. " +
                "Please verify and provide the required information before trying again.");
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setValidationErrors(details); // asigna la List 'details' al dto 'ApiError'

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(exception.getLocalizedMessage());
        apiError.setMessage(exception.getMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
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

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiError> handleMultiPartException(MultipartException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage(e.getMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleMaxUploadSizeExceedException(MaxUploadSizeExceededException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage(e.getMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(apiError);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiError> handleMailException(MailException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setMessage("Error al enviar email. " + e.getMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

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


}
