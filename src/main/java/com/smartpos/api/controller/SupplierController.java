package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateSupplierRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.SupplierResponse;
import com.smartpos.api.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j(topic = "SUPPLIER-CONTROLLER")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/suppliers")
    public ResponseData<SupplierResponse> createSupplier(@Valid @RequestBody CreateSupplierRequest request){
        log.info("Received request to create supplier with name: {}", request.getName());

        SupplierResponse supplierResponse = this.supplierService.createSupplier(request);

        return ResponseData.<SupplierResponse>builder()
                .code(201)
                .message("Supplier created successfully")
                .data(supplierResponse)
                .build();
    }

    @PutMapping("/suppliers/{id}")
    public ResponseData<SupplierResponse> updateSupplier(@PathVariable Long id,
                                                         @Valid @RequestBody CreateSupplierRequest request){
        log.info("Received request to update supplier with id: {}", id);

        SupplierResponse supplierResponse = this.supplierService.updateSupplier(id, request);

        return ResponseData.<SupplierResponse>builder()
                .code(200)
                .message("Supplier updated successfully")
                .data(supplierResponse)
                .build();
    }

    @GetMapping("/suppliers/{id}")
    public ResponseData<SupplierResponse> getSupplierById(@PathVariable Long id){
        log.info("Received request to get supplier with id: {}", id);

        SupplierResponse supplierResponse = this.supplierService.getSupplierById(id);

        return ResponseData.<SupplierResponse>builder()
                .code(200)
                .message("Supplier retrieved successfully")
                .data(supplierResponse)
                .build();
    }

    @DeleteMapping("/suppliers/{id}")
    public ResponseData<Void> deleteSupplier(@PathVariable Long id){
        log.info("Received request to delete supplier with id: {}", id);

        this.supplierService.deleteSupplier(id);

        return ResponseData.<Void>builder()
                .code(200)
                .message("Supplier deleted successfully")
                .build();
    }
}
