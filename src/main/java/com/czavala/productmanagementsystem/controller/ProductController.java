package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.ProductDto;
import com.czavala.productmanagementsystem.dto.SaveProductDto;
import com.czavala.productmanagementsystem.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ASSISTANT_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> product = productService.findProductById(id);
        return product.map(productFound -> ResponseEntity.ok(productFound)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid SaveProductDto saveProductDto) throws IOException {
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
