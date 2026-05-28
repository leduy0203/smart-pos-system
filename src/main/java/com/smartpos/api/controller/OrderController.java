package com.smartpos.api.controller;

import com.smartpos.api.model.request.CreateOrderRequest;
import com.smartpos.api.model.response.OrderResponse;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j(topic = "ORDER-CONTROLLER")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/orders")
    @Operation(summary = "Create a new order"
            , description = "Creates a new order with table id and list of product id with quantity")
    public ResponseData<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request){
        log.info("Received request to create order for table id: {}", request.getTableId());

        OrderResponse orderResponse = orderService.createOrder(request);

        return ResponseData.<OrderResponse>builder()
                .code(201)
                .message("Order created successfully")
                .data(orderResponse)
                .build();
    }
}
