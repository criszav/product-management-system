package com.czavala.productmanagementsystem.controller;

import com.czavala.productmanagementsystem.dto.ProductDto;
import com.czavala.productmanagementsystem.dto.SaveProductDto;
import com.czavala.productmanagementsystem.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> product = productService.findProductById(id);
        return product.map(productFound -> ResponseEntity.ok(productFound)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid SaveProductDto saveProductDto) {
        ProductDto newProduct = productService.saveProduct(saveProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody @Valid SaveProductDto saveProductDto) {
        ProductDto product = productService.updateProductById(id, saveProductDto);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<ProductDto> disableProductById(@PathVariable Long id) {
        ProductDto product = productService.disableProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/find/{product-name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable(value = "product-name") String productName) {
        List<ProductDto> products = productService.findProductsByName(productName);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }
}
