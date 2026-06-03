package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateSupplierRequest;
import com.smartpos.api.model.response.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse createSupplier(CreateSupplierRequest request);
    SupplierResponse updateSupplier(Long id, CreateSupplierRequest request);
    SupplierResponse getSupplierById(Long id);
    void deleteSupplier(Long id);

    List<SupplierResponse> getAllSuppliers(String keyword, String sort, int page, int size);
}
