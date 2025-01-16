package com.flagak.task_backend.components.orders.services;

public interface OrderService {
    void checkout(String customerEmail, String paymentType);
}
