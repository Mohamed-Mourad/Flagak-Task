package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.CartEntity;
import com.flagak.task_backend.models.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCart(CartEntity cart);
}
