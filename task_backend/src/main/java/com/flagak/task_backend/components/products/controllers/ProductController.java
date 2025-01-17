package com.flagak.task_backend.components.products.controllers;

import com.flagak.task_backend.components.products.dtos.ProductRequestDTO;
import com.flagak.task_backend.components.products.dtos.ProductResponseDTO;
import com.flagak.task_backend.components.products.services.ProductService;
import com.flagak.task_backend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    public ProductController(ProductService productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(
            @Valid @RequestBody ProductRequestDTO request,
            @RequestHeader("Authorization") String token) {

        String vendorEmail = jwtUtil.extractEmailFromToken(token.substring(7)); // Remove "Bearer "
        ProductResponseDTO response = productService.addProduct(request, vendorEmail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductResponseDTO> editProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDTO request,
            @RequestHeader("Authorization") String token) {

        String vendorEmail = jwtUtil.extractEmailFromToken(token.substring(7));
        ProductResponseDTO response = productService.editProduct(id, request, vendorEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String token) {

        String vendorEmail = jwtUtil.extractEmailFromToken(token.substring(7));
        productService.deleteProduct(id, vendorEmail);
        return ResponseEntity.noContent().build();
    }
}
