package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.CartEntity;
import com.flagak.task_backend.models.entities.CartItemEntity;
import com.flagak.task_backend.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCart(CartEntity cart);

    Optional<CartItemEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);
}
