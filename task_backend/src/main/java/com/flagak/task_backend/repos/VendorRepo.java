package com.flagak.task_backend.repos;

import com.flagak.task_backend.models.entities.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepo extends JpaRepository<VendorEntity, UUID> {
    Optional<VendorEntity> findByBusinessCertificateNumber(String businessCertificateNumber);
    Optional<VendorEntity> findByEmail(String email);
    Optional<VendorEntity> findFirstByBusinessName(String name);
}
