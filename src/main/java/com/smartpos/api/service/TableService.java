package com.smartpos.api.service;

import com.smartpos.api.common.TableStatus;
import com.smartpos.api.model.request.CreateTableRequest;
import com.smartpos.api.model.response.TableResponse;

import java.util.List;

public interface TableService {

    TableResponse createTable(CreateTableRequest request);

    TableResponse updateTable(Long id, CreateTableRequest request);

    TableResponse getTableById(Long id);

    List<TableResponse> getAllTables();

    void deleteTable(Long id);

    void updateTableStatus(Long id, TableStatus status);
}


