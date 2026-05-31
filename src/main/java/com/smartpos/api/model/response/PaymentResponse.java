package com.smartpos.api.model.response;

import com.smartpos.api.common.PaymentMethod;
import com.smartpos.api.common.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private BigDecimal amountReceived;

    private BigDecimal changeAmount;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private String transactionCode;

    private String paymentUrl;

    private LocalDateTime paymentTime;

    private LocalDateTime createdAt;
}
