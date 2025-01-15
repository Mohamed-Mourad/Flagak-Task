package com.flagak.task_backend.components.customers.controllers;

import com.flagak.task_backend.components.customers.dtos.CustomerRegisterRequestDTO;
import com.flagak.task_backend.components.customers.dtos.CustomerResponseDTO;
import com.flagak.task_backend.components.customers.services.CustomerService;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String token = customerService.login(loginRequest);
        return ResponseEntity.ok(token);
    }
}