package com.flagak.task_backend.components.carts.controllers;

import com.flagak.task_backend.components.carts.dtos.CartRequestDTO;
import com.flagak.task_backend.components.carts.dtos.CartResponseDTO;
import com.flagak.task_backend.components.carts.dtos.CartRequestDTO;
import com.flagak.task_backend.components.carts.dtos.RemoveFromCartResponseDTO;
import com.flagak.task_backend.components.carts.services.CartService;
import com.flagak.task_backend.components.customers.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CustomerService customerService;
    private final CartService cartService;

    public CartController(CustomerService customerService, CartService cartService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addItemToCart(
            @RequestBody CartRequestDTO request, @AuthenticationPrincipal String customerEmail) {
        cartService.addItemToCart(customerEmail, request);
        return ResponseEntity.ok(new CartResponseDTO("Item added to cart successfully"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(
            @RequestBody CartRequestDTO request, @AuthenticationPrincipal String customerEmail) {
        cartService.removeItemFromCart(customerEmail, request);
        return ResponseEntity.ok(new CartResponseDTO("Item removed from cart successfully"));
    }

}
