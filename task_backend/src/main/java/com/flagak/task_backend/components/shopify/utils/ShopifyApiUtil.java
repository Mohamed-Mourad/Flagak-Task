package com.flagak.task_backend.components.shopify.utils;

import org.springframework.stereotype.Component;
import com.flagak.task_backend.components.shopify.config.ShopifyConfig;

@Component
public class ShopifyApiUtil {

    private final ShopifyConfig shopifyConfig;

    public ShopifyApiUtil(ShopifyConfig shopifyConfig) {
        this.shopifyConfig = shopifyConfig;
    }

    public String buildApiUrl(String endpoint) {
        return shopifyConfig.getBaseUrl() + endpoint;
    }

    // Add utility methods for handling API authentication, headers, etc.
}
