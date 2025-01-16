package com.flagak.task_backend.components.carts.services;

import com.flagak.task_backend.components.carts.dtos.CartRequestDTO;
import com.flagak.task_backend.components.carts.dtos.RemoveFromCartRequestDTO;

import java.util.UUID;

public interface CartService {
    void addItemToCart(String customerEmail, CartRequestDTO request);

    void removeItemFromCart(String customerEmail, CartRequestDTO request);
}
