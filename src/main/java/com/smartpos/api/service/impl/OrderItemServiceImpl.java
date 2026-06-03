package com.smartpos.api.service.impl;

import com.smartpos.api.common.OrderItemStatus;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.Order;
import com.smartpos.api.model.OrderItem;
import com.smartpos.api.model.response.CancelOrderItemResponse;
import com.smartpos.api.repository.OrderItemRepository;
import com.smartpos.api.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.smartpos.api.common.OrderStatus.CANCELLED;
import static com.smartpos.api.common.TableStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ORDER-ITEM-SERVICE")
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CancelOrderItemResponse cancelOrderItem(Long orderItemId) {
        log.info("Cancelling order item with id: {}", orderItemId);

        OrderItem orderItem = getOrderItem(orderItemId);

        OrderItemStatus previous = orderItem.getStatus();

        validateStatus(orderItem);

        orderItem.setStatus(OrderItemStatus.CANCELLED);

        Order order = orderItem.getOrder();

        BigDecimal newSubTotal = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {

            if (item.getStatus() != OrderItemStatus.CANCELLED) {

                BigDecimal itemTotal =
                        item.getUnitPrice().multiply(
                                BigDecimal.valueOf(item.getQuantity())
                        );

                newSubTotal = newSubTotal.add(itemTotal);
            }
        }

        order.setSubTotal(newSubTotal);
        order.setTotalPrice(newSubTotal);

        updateOrderStatus(order);

        orderItemRepository.save(orderItem);

        log.info("Order item with id: {} has been cancelled. New total price for order {} is {}", orderItemId, order.getId(), newSubTotal);

        return CancelOrderItemResponse.builder()
                .itemId(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .status(OrderItemStatus.CANCELLED)
                .previousStatus(previous)
                .build();
    }

    private OrderItem getOrderItem(Long id){
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));
    }

    private void validateStatus(OrderItem orderItem){
        if (orderItem.getStatus() == OrderItemStatus.CANCELLED) {
            throw new AppException(ErrorCode.ORDER_ITEM_ALREADY_CANCELLED);
        }

        if (orderItem.getStatus() == OrderItemStatus.SERVED) {
            throw new AppException(ErrorCode.ORDER_ITEM_ALREADY_SERVED);
        }

        if (orderItem.getStatus() == OrderItemStatus.COOKING){
            throw new AppException(ErrorCode.ORDER_ITEM_ALREADY_COOKING);
        }
    }

    private void updateOrderStatus(Order order) {

        boolean allCancelled = order.getOrderItems()
                .stream()
                .allMatch(i -> i.getStatus() == OrderItemStatus.CANCELLED);

        if (allCancelled) {
            order.setStatus(CANCELLED);
            order.getTable().setStatus(AVAILABLE);
        }
    }
}
