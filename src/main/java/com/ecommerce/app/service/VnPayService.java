package com.ecommerce.app.service;

import java.math.BigDecimal;

public interface VnPayService {

    String createVnPayPaymentUrl(String orderId, BigDecimal amount, String ipAddr);
}
