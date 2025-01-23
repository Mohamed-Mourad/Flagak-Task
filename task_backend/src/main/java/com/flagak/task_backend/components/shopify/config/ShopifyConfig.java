package com.flagak.task_backend.components.shopify.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class ShopifyConfig {
    private String baseUrl = "https://flagak-task-1.myshopify.com/admin/api/2025-01/";
    private String apiKey = "xxx";
    private String password = "xxx";
}