package com.flagak.task_backend.components.customers.services;


import com.flagak.task_backend.components.customers.dtos.CustomerRegisterRequestDTO;
import com.flagak.task_backend.components.customers.dtos.CustomerResponseDTO;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CustomerService {
        CustomerResponseDTO registerCustomer(CustomerRegisterRequestDTO request);
        String login(LoginRequestDTO loginRequest);

        UUID getCustomerIdByEmail(String email);
}

