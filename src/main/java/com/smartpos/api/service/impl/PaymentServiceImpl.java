package com.smartpos.api.service.impl;

import com.smartpos.api.common.*;
import com.smartpos.api.configuration.VNPayConfig;
import com.smartpos.api.exception.AppException;
import com.smartpos.api.exception.ErrorCode;
import com.smartpos.api.model.*;
import com.smartpos.api.model.request.CashPaymentRequest;
import com.smartpos.api.model.request.VNPayPaymentRequest;
import com.smartpos.api.model.response.PaymentResponse;
import com.smartpos.api.repository.*;
import com.smartpos.api.service.PaymentService;
import com.smartpos.api.service.VNPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j(topic = "PAYMENT-SERVICE")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VNPayConfig vnpayConfig;
    private final VNPayService vnPayService;
    private static final Long USER_ID = 1L;

    @Override
    public PaymentResponse payByCash(CashPaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new AppException(ErrorCode.ORDER_NOT_COMPLETE);
        }

        BigDecimal total = order.getTotalPrice();

        if (request.getAmountReceived().compareTo(total) < 0) {
            throw new AppException(ErrorCode.INSUFFICIENT_AMOUNT);
        }

        User user = userRepository.findById(USER_ID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Payment payment = Payment.builder()
                .order(order)
                .amount(total)
                .paymentMethod(PaymentMethod.CASH)
                .status(PaymentStatus.SUCCESS)
                .transactionCode(generateTransactionCode())
                .paymentTime(LocalDateTime.now())
                .paidBy(user)
                .build();

        paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);

        return toResponse(payment);
    }

    @Override
    public PaymentResponse createVNPayPayment(VNPayPaymentRequest request , String clientIp ) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new AppException(ErrorCode.ORDER_NOT_COMPLETE);
        }

        User user = userRepository.findById(USER_ID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String transactionCode = generateTransactionCode();

        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalPrice())
                .paymentMethod(PaymentMethod.VNPAY)
                .status(PaymentStatus.PENDING)
                .transactionCode(transactionCode)
                .paidBy(user)
                .build();

        paymentRepository.save(payment);

        String paymentUrl = vnPayService.createPaymentUrl(
                order.getId(),
                order.getTotalPrice().longValue(),
                clientIp,
                transactionCode
        );

        PaymentResponse response = toResponse(payment);
        response.setPaymentUrl(paymentUrl);

        return response;
    }

    @Override
    @Transactional
    public PaymentResponse handleVNPayCallback(
            String txnRef,
            String responseCode,
            String transactionNo,
            String secureHash,
            Map<String, String> params
    ) {

        Payment payment = paymentRepository.findByTransactionCode(txnRef)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return toResponse(payment);
        }

        if (!verifySignature(params, secureHash)) {
            throw new AppException(ErrorCode.INVALID_SIGNATURE);
        }

        if ("00".equals(responseCode)) {

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setExternalTransactionId(transactionNo);
            payment.setPaymentTime(LocalDateTime.now());

            Order order = payment.getOrder();
            order.setStatus(OrderStatus.PAID);

            RestaurantTable table = order.getTable();
            table.setStatus(TableStatus.AVAILABLE);

        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);

        return toResponse(payment);
    }


    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        return null;
    }


    private boolean verifySignature(Map<String, String> params, String receivedHash) {

        Map<String, String> filtered = new TreeMap<>();

        params.forEach((k, v) -> {
            if (v != null
                    && !v.isBlank()
                    && !k.equals("vnp_SecureHash")
                    && !k.equals("vnp_SecureHashType")) {
                filtered.put(k, v);
            }
        });

        String query = vnpayConfig.buildQuery(filtered);

        String generated = vnpayConfig.hmacSHA512(
                vnpayConfig.getHashSecret(),
                query
        );

        return generated.equalsIgnoreCase(receivedHash);
    }


    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionCode(payment.getTransactionCode())
                .paymentTime(payment.getPaymentTime())
                .build();
    }


    private String generateTransactionCode() {
        return "TXN" + System.currentTimeMillis();
    }
}