package com.smartpos.api.service;

import com.smartpos.api.configuration.VNPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VNPayService {

    private final VNPayConfig config;

    public String createPaymentUrl(Long orderId, long amount, String ipAddress, String txnRef) {

        Map<String, String> vnpParams = new HashMap<>();

        String createDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", config.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", "Payment for order " + orderId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", config.getReturnUrl());
        vnpParams.put("vnp_IpAddr", ipAddress);
        vnpParams.put("vnp_CreateDate", createDate);

        log.info("VNPay Params: {}", vnpParams);

        String encodedQuery = config.buildQuery(vnpParams);
        log.info("Encoded Query (for signature): {}", encodedQuery);

        String secureHash = config.hmacSHA512(config.getHashSecret(), encodedQuery);
        log.info("Secure Hash: {}", secureHash);

        String paymentUrl = config.getPayUrl()
                + "?" + encodedQuery
                + "&vnp_SecureHash=" + secureHash;

        log.info("Payment URL (first 200 chars): {}", paymentUrl.substring(0, Math.min(200, paymentUrl.length())));

        return paymentUrl;
    }
}

