package com.ecommerce.app.service.impl;

import com.ecommerce.app.service.VnPayService;

import com.ecommerce.app.utils.VnPayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VnPayServiceImpl implements VnPayService {

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    @Value("${vnpay.vnpayUrl}")
    private String payUrl;

    @Override
    public String createVnPayPaymentUrl(String orderId, BigDecimal amount, String ipAddr) {
        try {
            String vnpVersion = "2.1.0";
            String vnpCommand = "pay";
            String vnpCurrCode = "VND";
            String vnpLocale = "vn";
            String vnpOrderType = "100000";

            String vnpTxnRef = orderId;
            String vnpIpAddr = ipAddr;
            BigDecimal amountForVNPay = amount.multiply(BigDecimal.valueOf(100));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String vnpCreateDate = LocalDateTime.now().format(formatter);
            String vnpExpireDate = LocalDateTime.now().plusMinutes(15).format(formatter);

            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", vnpVersion);
            vnpParams.put("vnp_Command", vnpCommand);
            vnpParams.put("vnp_TmnCode", tmnCode); // = HXN6O1HR
            vnpParams.put("vnp_Amount", String.valueOf(amountForVNPay.longValue()));
            vnpParams.put("vnp_CurrCode", vnpCurrCode);
            vnpParams.put("vnp_TxnRef", vnpTxnRef);
            vnpParams.put("vnp_OrderInfo", "Thanh toan don hang: " + orderId);
            vnpParams.put("vnp_OrderType", vnpOrderType);
            vnpParams.put("vnp_Locale", vnpLocale);
            vnpParams.put("vnp_ReturnUrl", returnUrl);
            vnpParams.put("vnp_IpAddr", vnpIpAddr);
            vnpParams.put("vnp_CreateDate", vnpCreateDate);
            vnpParams.put("vnp_ExpireDate", vnpExpireDate);

            // Sắp xếp key
            List<String> sortedKeys = new ArrayList<>(vnpParams.keySet());
            Collections.sort(sortedKeys);

            // Build hashData (KHÔNG encode)
            StringBuilder hashData = new StringBuilder();
            for (int i = 0; i < sortedKeys.size(); i++) {
                String key = sortedKeys.get(i);
                String value = vnpParams.get(key);
                if (value != null && !value.isEmpty()) {
                    hashData.append(key).append('=').append(value);
                    if (i < sortedKeys.size() - 1) {
                        hashData.append('&');
                    }
                }
            }

            // Tính secure hash
            String secureHash = VnPayUtils.hmacSHA512(hashSecret, hashData.toString());

            // Build query string (CÓ encode)
            StringBuilder query = new StringBuilder();
            for (int i = 0; i < sortedKeys.size(); i++) {
                String key = sortedKeys.get(i);
                String value = vnpParams.get(key);
                if (value != null && !value.isEmpty()) {
                    query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII))
                            .append('=')
                            .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                    if (i < sortedKeys.size() - 1) {
                        query.append('&');
                    }
                }
            }

            // Append chữ ký vào cuối URL
            query.append("&vnp_SecureHash=").append(secureHash);
            System.out.println("=== DEBUG VNPay ===");
            System.out.println("HashData: " + hashData);
            System.out.println("SecureHash: " + secureHash);
            System.out.println("Full URL: " + payUrl + "?" + query.toString());

            return payUrl + "?" + query.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo URL thanh toán VNPay", e);
        }

    }


}
