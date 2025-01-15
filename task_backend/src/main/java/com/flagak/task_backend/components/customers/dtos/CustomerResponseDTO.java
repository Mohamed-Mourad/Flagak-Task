package com.flagak.task_backend.components.customers.dtos;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private String customerId;
    private String name;
    private String email;
    private String createdAt;
}
