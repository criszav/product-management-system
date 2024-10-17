package com.czavala.productmanagementsystem.dto.productCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveCategoryDto implements Serializable {

    @NotBlank(message = "{generic.notblank}")
    private String name;
}
