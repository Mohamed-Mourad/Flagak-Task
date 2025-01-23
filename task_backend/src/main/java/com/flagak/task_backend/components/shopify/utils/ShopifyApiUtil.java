package com.flagak.task_backend.components.shopify.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import com.flagak.task_backend.components.shopify.config.ShopifyConfig;

import java.util.Base64;

@Component
public class ShopifyApiUtil {

    private final ShopifyConfig shopifyConfig;

    public ShopifyApiUtil(ShopifyConfig shopifyConfig) {
        this.shopifyConfig = shopifyConfig;
    }

    public String buildApiUrl(String endpoint) {
        return shopifyConfig.getBaseUrl() + endpoint;
    }

    public HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // String auth = shopifyConfig.getApiKey() + ":" + shopifyConfig.getPassword();
        // String auth = "" + ":" + "";
        // String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        //headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("X-Shopify-Access-Token", "XXX");
        return headers;
    }
}
