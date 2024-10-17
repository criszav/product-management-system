package com.czavala.productmanagementsystem.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileDto implements Serializable {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;
    @JsonProperty("created_at") @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonProperty("last_modified") @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModified;
    @JsonProperty("account_enabled")
    private boolean accountEnabled;
    private Collection<? extends GrantedAuthority> authorities;

}
