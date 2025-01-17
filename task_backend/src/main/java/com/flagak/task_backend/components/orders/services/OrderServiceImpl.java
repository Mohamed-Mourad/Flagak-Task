package com.flagak.task_backend.components.orders.services;

import com.flagak.task_backend.models.entities.*;
import com.flagak.task_backend.repos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepo cartRepo;

    private final CartItemRepo cartItemRepo;

    private final CustomerRepo customerRepo;

    private final ProductRepo productRepo;

    private final OrderRepo orderRepo;

    public OrderServiceImpl(CartRepo cartRepo, CartItemRepo cartItemRepo, CustomerRepo customerRepo, ProductRepo productRepo, OrderRepo orderRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public void checkout(String customerEmail, String paymentType) {
        // Validate payment type
        if (!"card".equalsIgnoreCase(paymentType) && !"cod".equalsIgnoreCase(paymentType)) {
            throw new IllegalArgumentException("Invalid payment type. Choose 'card' or 'cod'.");
        }

        CustomerEntity customer = customerRepo.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        CartEntity cart = cartRepo.findByCustomer(customer)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for customer"));

        // Create a new OrderEntity
        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(calculateCartTotal(cart));
        order.setPaymentType(paymentType);
        order.setOrderItems(new ArrayList<>());

        // Deduct stock and populate order items
        for (CartItemEntity cartItem : cart.getCartItems()) {
            ProductEntity product = cartItem.getProduct();
            int updatedStock = product.getStockQuantity() - cartItem.getQuantity();

            if (updatedStock < 0) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
            }

            product.setStockQuantity(updatedStock);
            productRepo.save(product);

            // Add to order items
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getQuantity() * product.getPrice());
            order.getOrderItems().add(orderItem);
        }

        orderRepo.save(order);

        // Clear the cart
        cartItemRepo.deleteAll(cart.getCartItems());
        cartRepo.delete(cart);
    }

    private Double calculateCartTotal(CartEntity cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice() * item.getQuantity())
                .reduce(0.0, Double::sum);
    }
}

