package com.flagak.task_backend.components.products.services;

import com.flagak.task_backend.components.products.dtos.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
}
