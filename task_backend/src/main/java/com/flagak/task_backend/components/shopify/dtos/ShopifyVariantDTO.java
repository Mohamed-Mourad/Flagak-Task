package com.flagak.task_backend.components.shopify.dtos;

import lombok.Data;

@Data
public class ShopifyVariantDTO {
    private String price;
    private int inventory_quantity;
}
