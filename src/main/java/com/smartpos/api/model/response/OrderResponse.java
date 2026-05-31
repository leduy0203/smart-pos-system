package com.smartpos.api.model.response;

import com.smartpos.api.common.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String tableNumber;
    private OrderStatus status;
    private List<OrderItemResponse> orderItems;
    private BigDecimal totalPrice;
}
