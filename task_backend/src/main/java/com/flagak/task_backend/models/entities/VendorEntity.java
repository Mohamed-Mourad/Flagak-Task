package com.flagak.task_backend.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorEntity {

    @Id
    @Column(name = "vendor_id", updatable = false, nullable = false)
    private UUID vendorId = UUID.randomUUID();;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_certificate_number", nullable = false, unique = true)
    private String businessCertificateNumber;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
