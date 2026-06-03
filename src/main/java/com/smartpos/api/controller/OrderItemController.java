package com.smartpos.api.controller;

import com.smartpos.api.model.response.CancelOrderItemResponse;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j(topic = "ORDER_ITEM-CONTROLLER")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PatchMapping("/order-items/{id}/cancel")
    @Operation(summary = "Cancel an order item", description = "Cancels an order item by its ID")
    public ResponseData<CancelOrderItemResponse> cancelOrderItem(@PathVariable @Min(1) Long id) {
        log.info("Received request to cancel order item with ID: {}", id);

        CancelOrderItemResponse orderItemCanceled = orderItemService.cancelOrderItem(id);

        return ResponseData.<CancelOrderItemResponse>builder()
                .code(201)
                .message("Order item cancelled successfully")
                .data(orderItemCanceled)
                .build();
    }
}
