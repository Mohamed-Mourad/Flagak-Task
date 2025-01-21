package com.flagak.task_backend.components.shopify.services;

import org.springframework.stereotype.Service;

@Service
public class ShopifyService {

    private final ShopifyProductSyncService productSyncService;

    public ShopifyService(ShopifyProductSyncService productSyncService) {
        this.productSyncService = productSyncService;
    }

    public void syncProducts() {
        productSyncService.syncAllProducts();
    }

    // TODO: Methods for syncing orders, carts, customers, vendors, etc.
}
