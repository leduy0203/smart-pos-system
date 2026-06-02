package com.smartpos.api.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequest {

    @NotNull(message = "Table ID must not be null")
    @Min(value = 1, message = "Table ID must be a positive number")
    private Long tableId;

    @NotEmpty(message = "Order items must not be empty")
    @Valid
    private List<CreateOrderItemRequest> orderItems;
}
