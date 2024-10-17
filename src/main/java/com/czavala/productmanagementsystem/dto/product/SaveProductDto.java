package com.czavala.productmanagementsystem.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductDto implements Serializable {

    @NotBlank(message = "{generic.notblank}")
    private String name;

    @Positive(message = "{generic.positive}")
    private Long price;

    private MultipartFile image;

    @Positive(message = "{generic.positive}")
    @JsonProperty("category_id")
    private Long categoryId;
}
