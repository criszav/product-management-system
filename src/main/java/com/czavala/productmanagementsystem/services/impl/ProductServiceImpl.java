package com.czavala.productmanagementsystem.services.impl;

import com.czavala.productmanagementsystem.dto.product.ProductDto;
import com.czavala.productmanagementsystem.dto.product.SaveProductDto;
import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.mapper.ProductMapper;
import com.czavala.productmanagementsystem.persistance.Utils.Status;
import com.czavala.productmanagementsystem.persistance.entities.Category;
import com.czavala.productmanagementsystem.persistance.entities.Product;
import com.czavala.productmanagementsystem.persistance.repository.ProductRepository;
import com.czavala.productmanagementsystem.services.ProductService;
import com.czavala.productmanagementsystem.services.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public Page<ProductDto> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> productMapper.mapToProductDto(product));
    }

    @Override
    public Optional<ProductDto> findProductById(Long id) {
        return productRepository.findById(id).map(product -> productMapper.mapToProductDto(product));
    }

    @Override
    public ProductDto saveProduct(SaveProductDto saveProductDto) throws IOException {
        Product newProduct = new Product();
        newProduct.setName(saveProductDto.getName());
        newProduct.setPrice(saveProductDto.getPrice());
        newProduct.setStatus(Status.ENABLED); // producto queda activado inmediatamente al momento de ser creado

        Category category = new Category();
        category.setId(saveProductDto.getCategoryId());

        newProduct.setCategory(category);

        if (saveProductDto.getImage() != null) {
            try {
                String imageUrl = cloudinaryService.uploadImage(saveProductDto.getImage());
                newProduct.setImageUrl(imageUrl);
            } catch (MultipartException exception) {
                throw new MultipartException("Error uploading image: " + exception.getMessage());
            }
        }

        newProduct.setCreatedAt(LocalDateTime.now());

        productRepository.save(newProduct);

        return productMapper.mapToProductDto(newProduct);
    }

    @Override
    public ProductDto updateProductById(Long id, SaveProductDto saveProductDto) {
        Product productFromDB = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Product id: " + id));

        productFromDB.setName(saveProductDto.getName());
        productFromDB.setPrice(saveProductDto.getPrice());

        Category category = new Category();
        category.setId(saveProductDto.getCategoryId());

        productFromDB.setCategory(category);
        productFromDB.setLastModified(LocalDateTime.now());

        productRepository.save(productFromDB);
        return productMapper.mapToProductDto(productFromDB);
    }

    @Override
    public ProductDto disableProductById(Long id) {
        Product productFromDB = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. Product id: " + id));
        productFromDB.setStatus(Status.DISABLED);
        productFromDB.setLastModified(LocalDateTime.now());

        productRepository.save(productFromDB);
        return productMapper.mapToProductDto(productFromDB);
    }

    @Override
    public List<ProductDto> findProductsByName(String productName) {
        return productRepository.findByNameContainingIgnoreCase(productName).stream()
                .map(product -> productMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }
}
