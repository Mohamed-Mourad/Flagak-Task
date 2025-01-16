package com.flagak.task_backend.components.carts.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CartRequestDTO {
    private UUID productId;
    private int quantity;
}

