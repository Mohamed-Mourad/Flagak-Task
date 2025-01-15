package com.flagak.task_backend.domains.customers.controllers;

import com.flagak.task_backend.domains.customers.dtos.CustomerRegisterRequestDTO;
import com.flagak.task_backend.domains.customers.dtos.CustomerResponseDTO;
import com.flagak.task_backend.domains.customers.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@Valid @RequestBody CustomerRegisterRequestDTO requestDTO) {
        CustomerResponseDTO responseDTO = customerService.registerCustomer(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}