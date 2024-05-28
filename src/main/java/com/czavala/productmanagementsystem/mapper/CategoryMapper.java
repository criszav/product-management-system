package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.CategoryDto;
import com.czavala.productmanagementsystem.persistance.entities.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {


    public CategoryDto mapToCategoryDto(Category category) {
        if (category == null) return null;

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setStatus(category.getStatus().name());

        return categoryDto;
    }
}
