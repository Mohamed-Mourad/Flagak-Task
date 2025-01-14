package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.CartEntity;
import com.flagak.task_backend.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByCustomer(CustomerEntity customer);
}
