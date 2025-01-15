package com.flagak.task_backend.domains.vendors.controllers;

import com.flagak.task_backend.domains.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.domains.vendors.dtos.VendorResponseDTO;
import com.flagak.task_backend.domains.vendors.services.VendorService;
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
}
