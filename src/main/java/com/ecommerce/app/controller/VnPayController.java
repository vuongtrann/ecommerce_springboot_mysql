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
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class VnPayController {
    private final VnPayService vnPayService;

    @GetMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam String orderId,
                                           @RequestParam BigDecimal amount,
                                           HttpServletRequest request) {
        String ipAddr = request.getRemoteAddr();
        String paymentUrl = vnPayService.createVnPayPaymentUrl(orderId, amount, ipAddr);
        return ResponseEntity.ok(Collections.singletonMap("url", paymentUrl));
    }

}
