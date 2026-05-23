package com.smartpos.api.model.request;

import com.smartpos.api.common.TableStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTableRequest {

    @NotBlank(message = "Table number must not be blank")
    private String tableNumber;

    @NotNull(message = "Capacity must not be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Status must not be null")
    private TableStatus status;
}

