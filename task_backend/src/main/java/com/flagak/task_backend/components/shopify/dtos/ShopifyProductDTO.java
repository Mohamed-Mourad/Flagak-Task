package com.flagak.task_backend.components.shopify.dtos;

import lombok.Data;
import java.util.List;

@Data
public class ShopifyProductDTO {
    private String title;
    private String body_html;
    private String vendor_name;
    private List<ShopifyVariantDTO> variants;
}