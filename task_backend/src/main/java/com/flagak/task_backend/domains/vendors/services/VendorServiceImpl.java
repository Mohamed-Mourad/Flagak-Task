package com.flagak.task_backend.domains.vendors.services;

import com.flagak.task_backend.domains.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.domains.vendors.dtos.VendorResponseDTO;
import com.flagak.task_backend.models.entities.VendorEntity;
import com.flagak.task_backend.repos.VendorRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepo vendorRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public VendorServiceImpl(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public VendorResponseDTO registerVendor(VendorRegisterRequestDTO request) {
        // Create a new VendorEntity
        VendorEntity vendor = new VendorEntity();
        vendor.setBusinessName(request.getBusinessName());
        vendor.setBusinessCertificateNumber(request.getBusinessCertificateNumber());
        vendor.setBillingAddress(request.getBillingAddress());
        vendor.setEmail(request.getEmail());
        vendor.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save the vendor to the database
        VendorEntity savedVendor = vendorRepo.save(vendor);

        // Convert to VendorResponse DTO
        VendorResponseDTO response = new VendorResponseDTO();
        response.setVendorId(savedVendor.getVendorId().toString()); // Convert UUID to String
        response.setBusinessName(savedVendor.getBusinessName());
        response.setBusinessCertificateNumber(savedVendor.getBusinessCertificateNumber());
        response.setBillingAddress(savedVendor.getBillingAddress());
        response.setEmail(savedVendor.getEmail());
        response.setCreatedAt(savedVendor.getCreatedAt());

        return response;
    }
}
