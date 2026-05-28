package com.smartpos.api.model.response;

import com.smartpos.api.common.OrderItemStatus;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class OrderItemResponse {

    private Long productId;
    private String productName;
    private Integer quantity;
    private OrderItemStatus status;
    private BigDecimal subTotal;
    private String note;
}
