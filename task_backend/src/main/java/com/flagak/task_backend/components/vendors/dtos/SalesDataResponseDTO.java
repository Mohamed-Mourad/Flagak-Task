package com.flagak.task_backend.components.vendors.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDataResponseDTO {
    private UUID vendorId;
    private double totalReceived; // Total money received
    private double totalReceivable; // Total money receivable
    private double totalPayable; // Total payable (14% of receivable)
}
