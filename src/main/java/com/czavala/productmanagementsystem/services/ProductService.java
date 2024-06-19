package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.ProductDto;
import com.czavala.productmanagementsystem.dto.SaveProductDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAllProducts();

    Optional<ProductDto> findProductById(Long id);

    ProductDto saveProduct(SaveProductDto saveProductDto) throws IOException;

    ProductDto updateProductById(Long id, SaveProductDto saveProductDto);

    ProductDto disableProductById(Long id);

    List<ProductDto> findProductsByName(String productName);
}
