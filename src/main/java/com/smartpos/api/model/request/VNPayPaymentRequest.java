package com.smartpos.api.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VNPayPaymentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;
}
