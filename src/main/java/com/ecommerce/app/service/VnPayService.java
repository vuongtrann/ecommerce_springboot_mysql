package com.ecommerce.app.service;

import com.ecommerce.app.model.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface VnPayService {
    public String createOrder(Order order, String urlReturn);
    public int orderReturn(HttpServletRequest request);
    }
