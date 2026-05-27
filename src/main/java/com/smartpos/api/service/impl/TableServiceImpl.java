package com.smartpos.api.service.impl;

import com.smartpos.api.common.TableStatus;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.RestaurantTable;
import com.smartpos.api.model.request.CreateTableRequest;
import com.smartpos.api.model.response.TableResponse;
import com.smartpos.api.repository.TableRepository;
import com.smartpos.api.service.TableService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "TABLE-SERVICE")
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Override
    @Transactional
    public TableResponse createTable(CreateTableRequest request) {
        log.info("Creating table with number: {}", request.getTableNumber());

        if (tableRepository.existsByTableNumber(request.getTableNumber())) {
            throw new AppException(ErrorCode.TABLE_NUMBER_ALREADY_EXISTS);
        }

        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(request.getTableNumber())
                .capacity(request.getCapacity())
                .status(TableStatus.AVAILABLE)
                .active(true)
                .build();

        try {
            table = tableRepository.save(table);
        } catch (DataIntegrityViolationException e) {
            handleTableConstraintViolation(e);
        }

        log.info("Table created with id: {}", table.getId());

        return convertToResponse(table);
    }

    @Override
    @Transactional
    public TableResponse updateTable(Long id, CreateTableRequest request) {
        log.info("Updating table with id: {}", id);

        RestaurantTable table = getTableByIdOrThrow(id);

        if (tableRepository.existsByTableNumberAndIdNot(request.getTableNumber(), id)) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());

        try {
            table = tableRepository.save(table);
        } catch (DataIntegrityViolationException e) {
            handleTableConstraintViolation(e);
        }

        log.info("Table with id: {} has been updated", id);

        return convertToResponse(table);
    }

    @Override
    public TableResponse getTableById(Long id) {
        log.info("Getting table with id: {}", id);
        RestaurantTable table = getTableByIdOrThrow(id);
        return convertToResponse(table);
    }

    @Override
    public List<TableResponse> getAllTables() {
        log.info("Getting all tables");
        return tableRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void deleteTable(Long id) {
        log.info("Deleting table with id: {}", id);

        RestaurantTable table = getTableByIdOrThrow(id);
        table.setActive(false);
        tableRepository.save(table);

        log.info("Table with id: {} has been deleted", id);
    }

    @Override
    @Transactional
    public void updateTableStatus(Long id, TableStatus status) {
        log.info("Updating table status with id: {} to status: {}", id, status);

        RestaurantTable table = getTableByIdOrThrow(id);

        validateStatusTransition(table.getStatus(), status);

        table.setStatus(status);

        tableRepository.save(table);

        log.info("Table with id: {} has been updated to status: {}", id, status);
    }


    private void validateStatusTransition(TableStatus currentStatus, TableStatus newStatus) {
        log.debug("Validating table status transition from {} to {}", currentStatus, newStatus);

        if (currentStatus == newStatus) {
            log.warn("Cannot transition to the same status: {}", currentStatus);
            throw new AppException(ErrorCode.INVALID_TABLE_STATUS_TRANSITION);
        }

        boolean isValidTransition = switch (currentStatus) {
            case AVAILABLE -> newStatus == TableStatus.OCCUPIED || newStatus == TableStatus.RESERVED;
            case OCCUPIED, RESERVED -> newStatus == TableStatus.AVAILABLE;
            default -> false;
        };

        if (!isValidTransition) {
            log.warn("Invalid table status transition from {} to {}", currentStatus, newStatus);
            throw new AppException(ErrorCode.INVALID_TABLE_STATUS_TRANSITION);
        }

        log.debug("Status transition from {} to {} is valid", currentStatus, newStatus);
    }


    private RestaurantTable getTableByIdOrThrow(Long id) {
        log.info("Fetching table with id: {}", id);
        return tableRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));
    }

    private TableResponse convertToResponse(RestaurantTable table) {
        return TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .status(table.getStatus())
                .build();
    }

    private void handleTableConstraintViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause == null) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String message = rootCause.getMessage();

        if (message.contains("uk_table_number")) {
            throw new AppException(ErrorCode.TABLE_NUMBER_ALREADY_EXISTS);
        }

        throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
