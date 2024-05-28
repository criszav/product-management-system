package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.CategoryDto;
import com.czavala.productmanagementsystem.dto.SaveCategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    
    List<CategoryDto> findAllCategories();

    Optional<CategoryDto> findCategoryById(Long id);

    CategoryDto saveCategory(SaveCategoryDto saveCategoryDto);

    CategoryDto updateCategoryById(Long id, SaveCategoryDto saveCategoryDto);

    CategoryDto disableCategoryById(Long id);
}
