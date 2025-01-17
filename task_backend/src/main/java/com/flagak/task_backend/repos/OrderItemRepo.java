package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.OrderEntity;
import com.flagak.task_backend.models.entities.OrderItemEntity;
import com.flagak.task_backend.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItemEntity, UUID> {
    List<OrderItemEntity> findByOrder(OrderEntity cart);

    List<OrderItemEntity> findByProduct(ProductEntity product);
}
