package com.flagak.task_backend.domains.customers.services;


import com.flagak.task_backend.domains.customers.dtos.CustomerRegistrationRequestDTO;
import com.flagak.task_backend.domains.customers.dtos.CustomerResponseDTO;
import com.flagak.task_backend.models.entities.CustomerEntity;
import com.flagak.task_backend.repos.CustomerRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public CustomerResponseDTO registerCustomer(CustomerRegistrationRequestDTO requestDTO) {
        // Check for duplicate email
        if (customerRepo.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Map DTO to entity
        CustomerEntity customer = new CustomerEntity();
        customer.setName(requestDTO.getName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // Save to database
        CustomerEntity savedCustomer = customerRepo.save(customer);

        // Map entity to response DTO
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setCustomerId(savedCustomer.getCustomerId().toString());
        responseDTO.setName(savedCustomer.getName());
        responseDTO.setEmail(savedCustomer.getEmail());
        responseDTO.setCreatedAt(savedCustomer.getCreatedAt()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return responseDTO;
    }
}

