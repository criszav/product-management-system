package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.productCategory.CategoryDto;
import com.czavala.productmanagementsystem.dto.productCategory.SaveCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    
    Page<CategoryDto> findAllCategories(Pageable pageable);

    Optional<CategoryDto> findCategoryById(Long id);

    CategoryDto saveCategory(SaveCategoryDto saveCategoryDto);

    CategoryDto updateCategoryById(Long id, SaveCategoryDto saveCategoryDto);

    CategoryDto disableCategoryById(Long id);
}
