package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.OrderEntity;
import com.flagak.task_backend.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByCustomer(CustomerEntity customer);
}
