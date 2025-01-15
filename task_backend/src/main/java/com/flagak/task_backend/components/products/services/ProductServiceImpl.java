package com.flagak.task_backend.components.products.services;

import com.flagak.task_backend.components.products.dtos.ProductResponseDTO;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductEntity> products = productRepo.findAll();
        return products.stream()
                .map(product -> new ProductResponseDTO(
                        product.getProductId().toString(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getVendor().getBusinessName()
                ))
                .collect(Collectors.toList());
    }
}
