package com.flagak.task_backend.components.shopify.controllers;

import com.flagak.task_backend.components.shopify.services.ShopifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopify")
public class ShopifyController {

    private final ShopifyService shopifyService;

    public ShopifyController(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    @GetMapping("/sync-products")
    public String syncProducts() {
        shopifyService.syncProducts();
        return "Product sync initiated";
    }

    // Add endpoints for carts, orders, customers, vendors, etc.
}
