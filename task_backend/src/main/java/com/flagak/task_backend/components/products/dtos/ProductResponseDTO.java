package com.flagak.task_backend.components.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductResponseDTO {
    private String productId;
    private String productName;
    private BigDecimal price;
    private int stockQuantity;
    private String vendorName;
}
