package com.czavala.productmanagementsystem.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String to;
    private String from;
    private String subject;
    @JsonProperty("message_body") private String messageBody;
    @JsonProperty("content_type") private String contentType;
}
