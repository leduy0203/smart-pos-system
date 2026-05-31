package com.smartpos.api.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CashPaymentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Amount received is required")
    @DecimalMin(value = "0.01", message = "Amount received must be a positive value")
    private BigDecimal amountReceived;
}
