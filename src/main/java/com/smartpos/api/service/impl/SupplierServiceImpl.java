package com.smartpos.api.service.impl;

import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Product;
import com.smartpos.api.model.Supplier;
import com.smartpos.api.model.request.CreateSupplierRequest;
import com.smartpos.api.model.response.SupplierResponse;
import com.smartpos.api.repository.SupplierRepository;
import com.smartpos.api.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.smartpos.api.exception.ErrorCode.SUPPLIER_ALREADY_EXISTS;
import static com.smartpos.api.exception.ErrorCode.SUPPLIER_NOT_FOUND;

@Service
@Slf4j(topic = "SUPPLIER-SERVICE")
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(CreateSupplierRequest request) {
        log.info("Creating supplier with name: {}", request.getName());

        if (supplierRepository.existsByName(request.getName())) {
            throw new AppException(SUPPLIER_ALREADY_EXISTS);
        }

        if (supplierRepository.existsByPhone(request.getPhone())) {
            throw new AppException(ErrorCode.SUPPLIER_PHONE_ALREADY_EXISTS);
        }

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .active(true)
                .build();

        try {
            supplier = supplierRepository.save(supplier);

        } catch (DataIntegrityViolationException e) {

            handleSupplierConstraintViolation(e);
        }

        log.info("Supplier created with id: {}", supplier.getId());
        return toResponse(supplier);
    }

    @Override
    public SupplierResponse updateSupplier(Long id, CreateSupplierRequest request) {
        log.info("Updating supplier with id: {}", id);

        if (supplierRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AppException(SUPPLIER_ALREADY_EXISTS);
        }

        if (supplierRepository.existsByPhoneAndIdNot(request.getPhone(), id)) {
            throw new AppException(ErrorCode.SUPPLIER_PHONE_ALREADY_EXISTS);
        }

        Supplier supplier = getById(id);
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());

        try {
            supplier = supplierRepository.save(supplier);

        } catch (DataIntegrityViolationException e) {

            handleSupplierConstraintViolation(e);
        }

        log.info("Supplier with id: {} has been updated", id);
        return toResponse(supplier);
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        log.info("Getting supplier with id: {}", id);

        Supplier supplier = getById(id);

        log.info("Supplier with id: {} has been retrieved", id);

        return toResponse(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        log.info("Deleting supplier with id: {}", id);

        Supplier supplier = getById(id);
        supplier.setActive(false);
        supplierRepository.save(supplier);

        log.info("Supplier with id: {} has been deleted", id);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers(String keyword, String sort, int page, int size) {
        log.info("Getting all suppliers with keyword: {}, sort: {}, page: {}, size: {}"
                , keyword, sort, page, size);

        Sort sortOrder = Sort.by("id").ascending();

        if (StringUtils.hasLength(sort)){

            String[] part = sort.split(":");

            if (part.length == 2){
                String field = part[0].trim();
                String direction = part[1].trim();

                sortOrder = "desc".equalsIgnoreCase(direction) ?
                        Sort.by(field).descending() :
                        Sort.by(field).ascending();
            }
        }

        int pageNo = 0;

        if (page > 0) {
            pageNo = page - 1;
        }

        Page<Supplier> suppliers;

        Pageable pageable = PageRequest.of(pageNo, size, sortOrder);

        String searchKeyword = null;

        if (StringUtils.hasText(keyword)){
            searchKeyword = "%" + keyword.trim().toLowerCase() + "%";
        }

        if (searchKeyword != null) {

            searchKeyword = "%" + keyword.trim().toLowerCase() + "%";

            suppliers = supplierRepository.search(searchKeyword, pageable);

            log.info("Search suppliers with keyword: {}, sort: {}, page: {}, size: {}", keyword, sort, page, size);
        } else {
            suppliers = supplierRepository.findByActiveTrue( pageable);
        }

        return toPageResponse(suppliers);
    }


    private List<SupplierResponse> toPageResponse(Page<Supplier> suppliers) {

        return suppliers.getContent().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private Supplier getById(Long id){
        return supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new AppException(SUPPLIER_NOT_FOUND));
    }

    private void handleSupplierConstraintViolation(
            DataIntegrityViolationException ex
    ) {

        Throwable rootCause = ex.getRootCause();

        if (rootCause == null) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String message = rootCause.getMessage();

        if (message.contains("uk_supplier_name")) {
            throw new AppException(ErrorCode.SUPPLIER_ALREADY_EXISTS);
        }

        if (message.contains("uk_supplier_phone")) {
            throw new AppException(ErrorCode.SUPPLIER_PHONE_ALREADY_EXISTS);
        }

        throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private SupplierResponse toResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .build();
    }
}
