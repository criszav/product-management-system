package com.czavala.productmanagementsystem.persistance.repository;

import com.czavala.productmanagementsystem.dto.ProductDto;
import com.czavala.productmanagementsystem.persistance.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String productName);
}
