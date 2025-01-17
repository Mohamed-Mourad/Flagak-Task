package com.flagak.task_backend.components.customers.services;


import com.flagak.task_backend.components.customers.dtos.CustomerRegisterRequestDTO;
import com.flagak.task_backend.components.customers.dtos.CustomerResponseDTO;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;
import com.flagak.task_backend.models.entities.CustomerEntity;
import com.flagak.task_backend.repos.CustomerRepo;
import com.flagak.task_backend.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    //private final BCryptPasswordEncoder passwordEncoder;


    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
        //this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public CustomerResponseDTO registerCustomer(CustomerRegisterRequestDTO requestDTO) {
        // Check for duplicate email
        if (customerRepo.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Map DTO to entity
        CustomerEntity customer = new CustomerEntity();
        customer.setName(requestDTO.getName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPassword(requestDTO.getPassword());

        // To use when testing with actual registered users:
        //customer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

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

    @Override
    public String login(LoginRequestDTO loginRequest) {
        var customer = customerRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (!Objects.equals(loginRequest.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // To use when testing with actual registered users:
//        if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
//            throw new IllegalArgumentException("Invalid email or password");
//        }

        return JwtUtil.generateToken(customer.getEmail());
    }

    @Override
    public UUID getCustomerIdByEmail(String email) {
        return customerRepo.findByEmail(email)
                .map(CustomerEntity::getCustomerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with email: " + email));
    }
}

