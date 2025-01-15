package com.flagak.task_backend.domains.vendors.services;

import com.flagak.task_backend.domains.vendors.dtos.VendorRegisterRequestDTO;
import com.flagak.task_backend.domains.vendors.dtos.VendorResponseDTO;

public interface VendorService {
    VendorResponseDTO registerVendor(VendorRegisterRequestDTO request);
}
