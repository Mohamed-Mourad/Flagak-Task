package com.flagak.task_backend.components.vendors.services;

import com.flagak.task_backend.components.vendors.dtos.SalesDataResponseDTO;
import com.flagak.task_backend.components.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.components.vendors.dtos.VendorResponseDTO;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;
import com.flagak.task_backend.models.entities.OrderItemEntity;
import com.flagak.task_backend.models.entities.ProductEntity;
import com.flagak.task_backend.models.entities.VendorEntity;
import com.flagak.task_backend.repos.OrderItemRepo;
import com.flagak.task_backend.repos.ProductRepo;
import com.flagak.task_backend.repos.VendorRepo;
import com.flagak.task_backend.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepo vendorRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public VendorServiceImpl(VendorRepo vendorRepo, OrderItemRepo orderItemRepo, ProductRepo productRepo) {
        this.vendorRepo = vendorRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        jwtUtil = new JwtUtil();
    }

    @Override
    public VendorResponseDTO registerVendor(VendorRegisterRequestDTO request) {
        // Create a new VendorEntity
        VendorEntity vendor = new VendorEntity();
        vendor.setBusinessName(request.getBusinessName());
        vendor.setBusinessCertificateNumber(request.getBusinessCertificateNumber());
        vendor.setBillingAddress(request.getBillingAddress());
        vendor.setEmail(request.getEmail());
        vendor.setPassword(request.getPassword());

        // To use when testing with actual registered users:
        //vendor.setPassword(passwordEncoder.encode(request.getPassword()));

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

    @Override
    public String login(LoginRequestDTO loginRequest) {
        var vendor = vendorRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!Objects.equals(loginRequest.getPassword(), vendor.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // To use when testing with actual registered users:
//        if (!passwordEncoder.matches(loginRequest.getPassword(), vendor.getPassword())) {
//            throw new IllegalArgumentException("Invalid email or password");
//        }

        return jwtUtil.generateToken(vendor.getEmail());
    }

    @Override
    public SalesDataResponseDTO getSalesData(String vendorEmail) {
        // Retrieve vendor details using email
        VendorEntity vendor = vendorRepo.findByEmail(vendorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found with email: " + vendorEmail));

        // Fetch all products for this vendor
        List<ProductEntity> vendorProducts = productRepo.findByVendor(vendor);

        double totalReceived = 0.0;
        double totalReceivable = 0.0;

        // For each product, fetch order items and calculate totals
        for (ProductEntity product : vendorProducts) {
            List<OrderItemEntity> orderItems = orderItemRepo.findByProduct(product);

            for (OrderItemEntity item : orderItems) {
                double itemTotal = item.getPrice() * item.getQuantity();

                if (item.getOrder().getPaymentType().equalsIgnoreCase("card")) {
                    totalReceived += itemTotal;
                } else if (item.getOrder().getPaymentType().equalsIgnoreCase("cod")) {
                    totalReceivable += itemTotal;
                }
            }
        }

        // Calculate total payable (14% of receivable)
        double totalPayable = totalReceivable * 0.14;

        return new SalesDataResponseDTO(vendor.getVendorId(), totalReceived, totalReceivable, totalPayable);
    }

}
