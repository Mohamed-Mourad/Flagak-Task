package com.flagak.task_backend.components.products.services;

import com.flagak.task_backend.components.products.dtos.ProductRequestDTO;
import com.flagak.task_backend.components.products.dtos.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO addProduct(ProductRequestDTO request, String vendorEmail);

    ProductResponseDTO editProduct(UUID productId, ProductRequestDTO request, String vendorEmail);

    void deleteProduct(UUID productId, String vendorEmail);
}
