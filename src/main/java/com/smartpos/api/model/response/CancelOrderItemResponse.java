package com.smartpos.api.model.response;

import com.smartpos.api.common.OrderItemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelOrderItemResponse {

    private Long itemId;
    private Long orderId;
    private OrderItemStatus status;
    private OrderItemStatus previousStatus;

}
