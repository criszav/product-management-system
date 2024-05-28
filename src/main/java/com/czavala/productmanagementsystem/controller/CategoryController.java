package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.CategoryDto;
import com.czavala.productmanagementsystem.dto.SaveCategoryDto;
import com.czavala.productmanagementsystem.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();

        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDto> category = categoryService.findCategoryById(id);
        return category.map(categoryFound -> ResponseEntity.ok(categoryFound)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody @Valid SaveCategoryDto saveCategoryDto) {
        CategoryDto newCategory = categoryService.saveCategory(saveCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id, @RequestBody @Valid SaveCategoryDto saveCategoryDto) {
        CategoryDto category = categoryService.updateCategoryById(id, saveCategoryDto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<CategoryDto> disableCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.disableCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
