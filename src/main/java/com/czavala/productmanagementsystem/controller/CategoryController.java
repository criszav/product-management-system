package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.CategoryDto;
import com.czavala.productmanagementsystem.dto.SaveCategoryDto;
import com.czavala.productmanagementsystem.services.CategoryService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();

        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDto> category = categoryService.findCategoryById(id);
        return category.map(categoryFound -> ResponseEntity.ok(categoryFound)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody @Valid SaveCategoryDto saveCategoryDto) {
        CategoryDto newCategory = categoryService.saveCategory(saveCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id, @RequestBody @Valid SaveCategoryDto saveCategoryDto) {
        CategoryDto category = categoryService.updateCategoryById(id, saveCategoryDto);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/disable")
    public ResponseEntity<CategoryDto> disableCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.disableCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
