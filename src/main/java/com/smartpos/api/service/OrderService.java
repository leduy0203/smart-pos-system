package com.smartpos.api.service;

import com.smartpos.api.model.request.CreateOrderRequest;
import com.smartpos.api.model.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrderDetail(Long id);
}
