package com.flagak.task_backend.components.vendors.services;

import com.flagak.task_backend.components.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.components.vendors.dtos.VendorResponseDTO;
import com.flagak.task_backend.models.dtos.LoginRequestDTO;

public interface VendorService {
    VendorResponseDTO registerVendor(VendorRegisterRequestDTO request);

    String login(LoginRequestDTO loginRequest);
}
