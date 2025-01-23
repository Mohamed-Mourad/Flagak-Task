package com.flagak.task_backend.components.shopify.services;

import com.flagak.task_backend.components.shopify.dtos.ShopifyProductDTO;
import com.flagak.task_backend.components.shopify.dtos.ShopifyVariantDTO;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.repos.ProductRepo;
import com.flagak.task_backend.components.shopify.utils.ShopifyApiUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ShopifyProductSyncService {

    private final ShopifyApiUtil shopifyApiUtil;
    private final ProductRepo productRepo;
    private final RestTemplate restTemplate;

    public ShopifyProductSyncService(ShopifyApiUtil shopifyApiUtil, ProductRepo productRepo) {
        this.shopifyApiUtil = shopifyApiUtil;
        this.productRepo = productRepo;
        this.restTemplate = new RestTemplate();
    }

    public void syncAllProducts() {
        String url = shopifyApiUtil.buildApiUrl("products.json");

        // Build headers with Basic Auth
        HttpHeaders headers = shopifyApiUtil.createAuthHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Fetch products from Shopify
        ResponseEntity<List<ShopifyProductDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            List<ShopifyProductDTO> products = response.getBody();
            saveProducts(products);
        } else {
            throw new RuntimeException("Failed to fetch products from Shopify: " + response.getStatusCode());
        }
    }


    private void saveProducts(List<ShopifyProductDTO> products) {
        for (ShopifyProductDTO productData : products) {
            String productName = productData.getTitle();
            String productDescription = productData.getBody_html();
            ShopifyVariantDTO firstVariant = productData.getVariants().get(0);
            Double price = Double.valueOf(firstVariant.getPrice());
            int stockQuantity = firstVariant.getInventory_quantity();

            ProductEntity product = new ProductEntity();
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            productRepo.save(product);
        }
    }

}
