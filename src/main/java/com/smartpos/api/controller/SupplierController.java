package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateSupplierRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.SupplierResponse;
import com.smartpos.api.service.SupplierService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j(topic = "SUPPLIER-CONTROLLER")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/suppliers")
    @Operation(summary = "Create a new supplier", description = "Creates a new supplier with the provided details.")
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
    @Operation(summary = "Update a supplier", description = "Updates an existing supplier with the provided details.")
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
    @Operation(summary = "Get a supplier by ID", description = "Retrieves a supplier by their unique ID.")
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
    @Operation(summary = "Delete a supplier", description = "Deletes a supplier by their unique ID.")
    public ResponseData<Void> deleteSupplier(@PathVariable Long id){
        log.info("Received request to delete supplier with id: {}", id);

        this.supplierService.deleteSupplier(id);

        return ResponseData.<Void>builder()
                .code(200)
                .message("Supplier deleted successfully")
                .build();
    }

    @GetMapping("/suppliers")
    @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers.")
    public ResponseData<List<SupplierResponse>> getAllSuppliers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        log.info("Received request to get all suppliers");

        List<SupplierResponse> supplierResponses = this.supplierService.getAllSuppliers(keyword, sort, page, size);

        return ResponseData.<List<SupplierResponse>>builder()
                .code(200)
                .message("Suppliers retrieved successfully")
                .data(supplierResponses)
                .build();
    }
}
