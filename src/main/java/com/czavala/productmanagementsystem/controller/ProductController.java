package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.product.ProductDto;
import com.czavala.productmanagementsystem.dto.product.SaveProductDto;
import com.czavala.productmanagementsystem.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("permitAll")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductDto> products = productService.findAllProducts(pageable);

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> product = productService.findProductById(id);
        return product.map(productFound -> ResponseEntity.ok(productFound)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> saveProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("category_id") Long categoryId
    ) throws IOException {

        SaveProductDto saveProductDto = new SaveProductDto();
        saveProductDto.setName(name);
        saveProductDto.setPrice(price);
        saveProductDto.setImage(image);
        saveProductDto.setCategoryId(categoryId);

        ProductDto newProduct = productService.saveProduct(saveProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody @Valid SaveProductDto saveProductDto) {
        ProductDto product = productService.updateProductById(id, saveProductDto);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/disable")
    public ResponseEntity<ProductDto> disableProductById(@PathVariable Long id) {
        ProductDto product = productService.disableProductById(id);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/find/{productName}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String productName) {
        List<ProductDto> products = productService.findProductsByName(productName);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }
}
