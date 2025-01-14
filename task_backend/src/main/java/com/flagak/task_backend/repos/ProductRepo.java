package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.models.entities.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByVendor(VendorEntity vendor);
}
