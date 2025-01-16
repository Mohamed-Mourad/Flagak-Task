package com.flagak.task_backend.components.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequestDTO {
    private String productName;
    private Double price;
    private int stockQuantity;
}
