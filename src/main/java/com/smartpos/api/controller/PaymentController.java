package com.smartpos.api.controller;

import com.smartpos.api.configuration.VNPayConfig;
import com.smartpos.api.model.request.CashPaymentRequest;
import com.smartpos.api.model.request.VNPayPaymentRequest;
import com.smartpos.api.model.response.PaymentResponse;
import com.smartpos.api.model.response.ResponseData;
import com.smartpos.api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j(topic = "PAYMENT-CONTROLLER")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final VNPayConfig vnpayConfig;

    @PostMapping("/cash")
    @Operation(summary = "Create a new cash payment for an order")
    public ResponseData<PaymentResponse> createCashPayment(@Valid CashPaymentRequest request) {
        log.info("Received request to create cash payment for order ID: {}", request.getOrderId());

        PaymentResponse paymentResponse = paymentService.payByCash(request);

        return ResponseData.<PaymentResponse>builder()
                .code(201)
                .message("Payment created successfully")
                .data(paymentResponse)
                .build();

    }

    @PostMapping("/vnpay")
    @Operation(summary = "Create a new VNPay payment for an order and generate the payment URL")
    public ResponseData<PaymentResponse> createVNPayPayment(
            @Valid
            @RequestBody
            VNPayPaymentRequest request,
            HttpServletRequest httpRequest
    ) {
        log.info("Received request to create VNPay payment for order ID: {}", request.getOrderId());

        String clientIp = vnpayConfig.getClientIp(httpRequest);

        PaymentResponse response = paymentService.createVNPayPayment(request ,clientIp );

        return ResponseData.<PaymentResponse>builder()
                .code(200)
                .message("VNPay URL generated")
                .data(response)
                .build();
    }

    @GetMapping("/vnpay-callback")
    public ResponseData<PaymentResponse> callback(
            @RequestParam Map<String, String> params
    ) {

        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");
        String secureHash = params.get("vnp_SecureHash");

        PaymentResponse response = paymentService.handleVNPayCallback(
                txnRef,
                responseCode,
                params.get("vnp_TransactionNo"),
                secureHash,
                params
        );

        return ResponseData.<PaymentResponse>builder()
                        .code(200)
                        .message("VNPay callback handled")
                        .data(response)
                        .build();
    }
}
