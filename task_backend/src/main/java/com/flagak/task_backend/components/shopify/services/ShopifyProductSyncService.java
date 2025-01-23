package com.flagak.task_backend.components.shopify.services;

import com.flagak.task_backend.components.shopify.dtos.ShopifyProductDTO;
import com.flagak.task_backend.components.shopify.dtos.ShopifyProductResponseDTO;
import com.flagak.task_backend.components.shopify.dtos.ShopifyVariantDTO;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.models.entities.VendorEntity;
import com.flagak.task_backend.repos.ProductRepo;
import com.flagak.task_backend.components.shopify.utils.ShopifyApiUtil;
import com.flagak.task_backend.repos.VendorRepo;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class ShopifyProductSyncService {

    private final ShopifyApiUtil shopifyApiUtil;
    private final ProductRepo productRepo;
    private final VendorRepo vendorRepo;
    private final RestTemplate restTemplate;

    public ShopifyProductSyncService(ShopifyApiUtil shopifyApiUtil, ProductRepo productRepo, VendorRepo vendorRepo) {
        this.shopifyApiUtil = shopifyApiUtil;
        this.productRepo = productRepo;
        this.vendorRepo = vendorRepo;
        this.restTemplate = new RestTemplate();
    }

    public void syncAllProducts() {
        String url = shopifyApiUtil.buildApiUrl("products.json");

        // Build headers with Basic Auth
        HttpHeaders headers = shopifyApiUtil.createAuthHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Fetch products from Shopify
        ResponseEntity<ShopifyProductResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ShopifyProductResponseDTO.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            ShopifyProductResponseDTO responseBody = response.getBody();
            List<ShopifyProductDTO> products = responseBody.getProducts();
            System.out.println(products);
            saveProducts(products);
        } else {
            throw new RuntimeException("Failed to fetch products from Shopify: " + response.getStatusCode());
        }
    }


    private void saveProducts(List<ShopifyProductDTO> products) {
        int i = 1;
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

            int finalI = i;

            String vendorName = productData.getVendor_name() != null
                    ? productData.getVendor_name()
                    : "nonsense for testing purposes " + finalI;

            VendorEntity vendor = vendorRepo.findFirstByBusinessName(productData.getVendor_name())
                    .orElseGet(() -> {
                        VendorEntity newVendor = new VendorEntity();
                        newVendor.setBusinessName(vendorName);
                        newVendor.setBusinessCertificateNumber("nonsense for testing purposes " + finalI);
                        newVendor.setBillingAddress("nonsense for testing purposes " + finalI);
                        newVendor.setEmail("nonsense for testing purposes " + finalI);
                        newVendor.setPassword("nonsense for testing purposes " + finalI);

                        return vendorRepo.save(newVendor); // Save and return the new vendor
                    });

            i++;
            product.setVendor(vendor);
            productRepo.save(product);
        }
    }

}
