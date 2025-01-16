package com.flagak.task_backend.components.carts.services;

import com.flagak.task_backend.components.carts.dtos.CartRequestDTO;
import com.flagak.task_backend.models.entities.CartEntity;
import com.flagak.task_backend.models.entities.CartItemEntity;
import com.flagak.task_backend.models.entities.CustomerEntity;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.repos.CartItemRepo;
import com.flagak.task_backend.repos.CartRepo;
import com.flagak.task_backend.repos.CustomerRepo;
import com.flagak.task_backend.repos.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;

    public CartServiceImpl(CartRepo cartRepo, CartItemRepo cartItemRepo, CustomerRepo customerRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Override
    public void addItemToCart(String customerEmail, CartRequestDTO request) {
        // Retrieve or create a cart for the customer
        CustomerEntity customer = customerRepo.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        CartEntity cart = cartRepo.findByCustomer(customer)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setCustomer(customer);
                    return cartRepo.save(newCart);
                });

        // Check if the product exists
        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Check stock availability
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // Check if the product is already in the cart
        CartItemEntity cartItem = cartItemRepo.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartItemEntity newItem = new CartItemEntity();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    return newItem;
                });

        // Update the quantity
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepo.save(cartItem);
    }


    @Override
    public void removeItemFromCart(String customerEmail, CartRequestDTO request) {
        // Retrieve the cart for the customer
        CustomerEntity customer = customerRepo.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        CartEntity cart = cartRepo.findByCustomer(customer)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // Check if the product exists
        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Find the cart item
        CartItemEntity cartItem = cartItemRepo.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in the cart"));

        // Update or remove the item
        if (cartItem.getQuantity() <= request.getQuantity()) {
            cartItemRepo.delete(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - request.getQuantity());
            cartItemRepo.save(cartItem);
        }
    }
}
