package com.flagak.task_backend.components.products.services;

import com.flagak.task_backend.components.products.dtos.ProductRequestDTO;
import com.flagak.task_backend.components.products.dtos.ProductResponseDTO;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.models.entities.VendorEntity;
import com.flagak.task_backend.repos.ProductRepo;
import com.flagak.task_backend.repos.VendorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final VendorRepo vendorRepo;

    public ProductServiceImpl(ProductRepo productRepo, VendorRepo vendorRepo) {
        this.productRepo = productRepo;
        this.vendorRepo = vendorRepo;
    }

    private ProductResponseDTO mapToResponseDTO(ProductEntity product) {
        return new ProductResponseDTO(
                product.getProductId().toString(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getVendor().getBusinessName()
        );
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductEntity> products = productRepo.findAll();
        return products.stream()
                .map(product -> new ProductResponseDTO(
                        product.getProductId().toString(),
                        product.getProductName(),
                        product.getProductDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getVendor().getBusinessName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO request, String vendorEmail) {
        VendorEntity vendor = vendorRepo.findByEmail(vendorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        ProductEntity product = new ProductEntity();
        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setVendor(vendor);

        ProductEntity savedProduct = productRepo.save(product);

        return mapToResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO editProduct(UUID productId, ProductRequestDTO request, String vendorEmail) {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getVendor().getEmail().equals(vendorEmail)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        ProductEntity updatedProduct = productRepo.save(product);
        return mapToResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID productId, String vendorEmail) {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getVendor().getEmail().equals(vendorEmail)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        productRepo.delete(product);
    }
}
