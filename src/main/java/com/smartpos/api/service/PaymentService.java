package com.smartpos.api.service;

import com.smartpos.api.model.request.CashPaymentRequest;
import com.smartpos.api.model.request.VNPayPaymentRequest;
import com.smartpos.api.model.response.PaymentResponse;

import java.util.Map;

public interface PaymentService {

    PaymentResponse payByCash(CashPaymentRequest request);

    PaymentResponse createVNPayPayment(VNPayPaymentRequest request , String clientIp);

    PaymentResponse handleVNPayCallback(
            String transactionCode,
            String responseCode,
            String transactionNo,
            String secureHash,
            Map<String, String> params
    );

    PaymentResponse getPaymentById(Long paymentId);

}
