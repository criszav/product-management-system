package com.czavala.productmanagementsystem.services.impl;

import com.czavala.productmanagementsystem.dto.productCategory.CategoryDto;
import com.czavala.productmanagementsystem.dto.productCategory.SaveCategoryDto;
import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.mapper.CategoryMapper;
import com.czavala.productmanagementsystem.persistance.Utils.Status;
import com.czavala.productmanagementsystem.persistance.entities.Category;
import com.czavala.productmanagementsystem.persistance.repository.CategoryRepository;
import com.czavala.productmanagementsystem.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> categoryMapper.mapToCategoryDto(category));
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> categoryMapper.mapToCategoryDto(category));
    }

    @Override
    public CategoryDto saveCategory(SaveCategoryDto saveCategoryDto) {
        Category newCategory = new Category();
        newCategory.setName(saveCategoryDto.getName());
        newCategory.setStatus(Status.ENABLED);
        newCategory.setCreatedAt(LocalDateTime.now());

        categoryRepository.save(newCategory);
        return categoryMapper.mapToCategoryDto(newCategory);
    }

    @Override
    public CategoryDto updateCategoryById(Long id, SaveCategoryDto saveCategoryDto) {
        Category categoryFromDB = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found. Category id: " + id));

        categoryFromDB.setName(saveCategoryDto.getName());
        categoryFromDB.setLastModified(LocalDateTime.now());
        categoryRepository.save(categoryFromDB);
        return categoryMapper.mapToCategoryDto(categoryFromDB);
    }

    @Override
    public CategoryDto disableCategoryById(Long id) {
        Category categoryFromDB = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found. Category id: " + id));
        categoryFromDB.setStatus(Status.DISABLED);
        categoryFromDB.setLastModified(LocalDateTime.now());
        categoryRepository.save(categoryFromDB);
        return categoryMapper.mapToCategoryDto(categoryFromDB);
    }
}
