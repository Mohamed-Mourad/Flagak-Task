package com.flagak.task_backend.domains.vendors.dtos;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class VendorRegisterRequestDTO {
    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business certificate number is required")
    private String businessCertificateNumber;

    @NotBlank(message = "Billing address is required")
    private String billingAddress;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
