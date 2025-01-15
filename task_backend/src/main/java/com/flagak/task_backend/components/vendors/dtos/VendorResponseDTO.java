package com.flagak.task_backend.components.vendors.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VendorResponseDTO {
    private String vendorId;
    private String businessName;
    private String businessCertificateNumber;
    private String billingAddress;
    private String email;
    private LocalDateTime createdAt;
}