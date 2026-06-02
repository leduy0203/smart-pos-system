package com.smartpos.api.service.impl;

import com.smartpos.api.common.OrderItemStatus;
import com.smartpos.api.common.OrderStatus;
import com.smartpos.api.common.TableStatus;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Order;
import com.smartpos.api.model.OrderItem;
import com.smartpos.api.model.Product;
import com.smartpos.api.model.RestaurantTable;
import com.smartpos.api.model.request.CreateOrderItemRequest;
import com.smartpos.api.model.request.CreateOrderRequest;
import com.smartpos.api.model.response.OrderItemResponse;
import com.smartpos.api.model.response.OrderResponse;
import com.smartpos.api.repository.OrderRepository;
import com.smartpos.api.repository.ProductRepository;
import com.smartpos.api.repository.TableRepository;
import com.smartpos.api.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "ORDER-SERVICE")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final TableRepository tableRepository;

    private final ProductRepository productRepository;

    private final Long userId = 1L;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for table id: {}", request.getTableId());

        RestaurantTable table = tableRepository.findByIdAndActiveTrue(request.getTableId())
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));

        Order order = Order.builder()
                .orderCode(generateOrderCode())
                .table(table)
                .status(OrderStatus.PENDING)
                .subTotal(BigDecimal.ZERO)
                .totalPrice(BigDecimal.ZERO)
                // .createdBy(currentUserId)
                .build();

        table.setStatus(TableStatus.OCCUPIED);

        BigDecimal totalOrderSubtotal = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (CreateOrderItemRequest itemRequest : request.getOrderItems()) {
            Product product = productRepository.findByIdAndIsAvailableTrue(itemRequest.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            BigDecimal itemSubtotal = product.getSellingPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalOrderSubtotal = totalOrderSubtotal.add(itemSubtotal);

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .order(order)
                    .subTotal(itemSubtotal)
                    .productName(product.getName())
                    .status(OrderItemStatus.ORDERED)
                    .note(itemRequest.getNote())
                    .unitPrice(product.getSellingPrice())
                    .costPrice(product.getCostPrice())
                    .build();

            items.add(item);
        }

        order.setOrderItems(items);
        order.setSubTotal(totalOrderSubtotal);
        order.setTotalPrice(totalOrderSubtotal);

        order = orderRepository.save(order);
        log.info("Order created with ID: {}", order.getId());


        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .tableNumber(order.getTable().getTableNumber())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems().stream().map(item -> OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .status(item.getStatus())
                        .subTotal(item.getSubTotal())
                        .note(item.getNote())
                        .build()).toList())
                .build();
    }

    private String generateOrderCode() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis();
        return "ORD-" + date + "-" + String.format("%04d", seq % 10000);
    }
}
