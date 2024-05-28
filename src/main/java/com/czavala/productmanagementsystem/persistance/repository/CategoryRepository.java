package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.persistance.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
