package com.flagak.task_backend.components.shopify.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyProductResponseDTO {
    private List<ShopifyProductDTO> products;
}
