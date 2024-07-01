package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.ProductDto;
import com.czavala.productmanagementsystem.persistance.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {


    public ProductDto mapToProductDto(Product product) {
        if (product == null) return null;

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setStatus(product.getStatus().name());
        productDto.setCategory(product.getCategory().getName());

        return productDto;
    }
}
