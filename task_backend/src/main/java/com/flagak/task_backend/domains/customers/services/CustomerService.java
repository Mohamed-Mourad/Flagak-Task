package com.flagak.task_backend.domains.customers.services;


import com.flagak.task_backend.domains.customers.dtos.CustomerRegisterRequestDTO;
import com.flagak.task_backend.domains.customers.dtos.CustomerResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
        CustomerResponseDTO registerCustomer(CustomerRegisterRequestDTO request);
}

