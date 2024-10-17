package com.czavala.productmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY) // incluye solo campos NO vacios en el json
public class ApiError implements Serializable {

    @JsonProperty("backend_message") private String backendMessage;
    private String message;
    private String url;
    private String method;
    @JsonProperty("validation_errors") private List<String> validationErrors;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") private LocalDateTime timestamp;
}
