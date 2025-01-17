package com.flagak.task_backend.components.vendors.controllers;

import com.flagak.task_backend.components.vendors.dtos.SalesDataResponseDTO;
import com.flagak.task_backend.components.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.components.vendors.dtos.VendorResponseDTO;
import com.flagak.task_backend.components.vendors.services.VendorService;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/register")
    public ResponseEntity<VendorResponseDTO> registerVendor(@Valid @RequestBody VendorRegisterRequestDTO request) {
        VendorResponseDTO response = vendorService.registerVendor(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String token = vendorService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/sales/{vendorEmail}")
    public ResponseEntity<SalesDataResponseDTO> getVendorSalesData(@PathVariable String vendorEmail) {
        SalesDataResponseDTO salesData = vendorService.getSalesData(vendorEmail);
        return ResponseEntity.ok(salesData);
    }
}
