package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.product.ProductDto;
import com.czavala.productmanagementsystem.dto.product.SaveProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAllProducts(Pageable pageable);

    Optional<ProductDto> findProductById(Long id);

    ProductDto saveProduct(SaveProductDto saveProductDto) throws IOException;

    ProductDto updateProductById(Long id, SaveProductDto saveProductDto);

    ProductDto disableProductById(Long id);

    List<ProductDto> findProductsByName(String productName);
}
