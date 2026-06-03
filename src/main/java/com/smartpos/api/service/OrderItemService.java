package com.smartpos.api.service;

import com.smartpos.api.model.response.CancelOrderItemResponse;

public interface OrderItemService {
    CancelOrderItemResponse cancelOrderItem(Long orderItemId);
}
