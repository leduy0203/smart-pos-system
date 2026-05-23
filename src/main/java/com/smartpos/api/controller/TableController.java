package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateTableRequest;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.model.response.TableResponse;
import com.smartpos.api.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j(topic = "TABLE-CONTROLLER")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping("/tables")
    @Operation(summary = "Create a new table", description = "Creates a new restaurant table")
    public ResponseData<TableResponse> createTable(@Valid @RequestBody CreateTableRequest request) {
        log.info("Received request to create table with number: {}", request.getTableNumber());

        TableResponse tableResponse = this.tableService.createTable(request);

        return ResponseData.<TableResponse>builder()
                .code(201)
                .message("Table created successfully")
                .data(tableResponse)
                .build();
    }

    @PutMapping("/tables/{id}")
    @Operation(summary = "Update a table", description = "Updates a table by id")
    public ResponseData<TableResponse> updateTable(@PathVariable Long id,
                                                    @Valid @RequestBody CreateTableRequest request) {
        log.info("Received request to update table with id: {}", id);

        TableResponse tableResponse = this.tableService.updateTable(id, request);

        return ResponseData.<TableResponse>builder()
                .code(200)
                .message("Table updated successfully")
                .data(tableResponse)
                .build();
    }

    @GetMapping("/tables/{id}")
    @Operation(summary = "Get a table", description = "Gets a table by id")
    public ResponseData<TableResponse> getTableById(@PathVariable Long id) {
        log.info("Received request to get table with id: {}", id);

        TableResponse tableResponse = this.tableService.getTableById(id);

        return ResponseData.<TableResponse>builder()
                .code(200)
                .message("Table retrieved successfully")
                .data(tableResponse)
                .build();
    }

    @GetMapping("/tables")
    @Operation(summary = "Get all tables", description = "Retrieves all restaurant tables")
    public ResponseData<List<TableResponse>> getAllTables() {
        log.info("Received request to get all tables");

        List<TableResponse> tableResponses = this.tableService.getAllTables();

        return ResponseData.<List<TableResponse>>builder()
                .code(200)
                .message("Tables retrieved successfully")
                .data(tableResponses)
                .build();
    }

    @DeleteMapping("/tables/{id}")
    @Operation(summary = "Delete a table", description = "Deletes a table by id")
    public ResponseData<Void> deleteTable(@PathVariable Long id) {
        log.info("Received request to delete table with id: {}", id);

        this.tableService.deleteTable(id);

        return ResponseData.<Void>builder()
                .code(200)
                .message("Table deleted successfully")
                .build();
    }
}
