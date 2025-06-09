package com.ecommerce.app.controller;

import com.ecommerce.app.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/vnpay")
@RequiredArgsConstructor
public class VnPayController {
    private final VnPayService vnPayService;

//    @GetMapping("/create")
//    public ResponseEntity<?> createPayment(@RequestParam String orderId,
//                                           @RequestParam BigDecimal amount,
//                                           HttpServletRequest request) {
//        String ipAddr = request.getRemoteAddr();
//        String paymentUrl = vnPayService.createVnPayPaymentUrl(orderId, amount, ipAddr);
//        return ResponseEntity.ok(Collections.singletonMap("url", paymentUrl));
//    }


    @GetMapping("/vnpay-callback")
    public String handleVNPayCallBack(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        // Lấy các thông tin cần gửi về FE
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Tạo URL redirect về FE với các tham số
        String redirectUrl = "http://127.0.0.1:8000/checkout?" +
                "orderId=" + orderInfo +
                "&totalPrice=" + totalPrice +
                "&paymentTime=" + paymentTime +
                "&transactionId=" + transactionId +
                "&status=" + (paymentStatus == 1 ? "success" : "fail");

        // Redirect về FE
        return "redirect:" + redirectUrl;
    }
}
