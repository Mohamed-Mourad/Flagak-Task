package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByCustomerId(UUID customerId);
}
