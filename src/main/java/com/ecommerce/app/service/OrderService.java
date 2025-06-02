package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderForm form);
    OrderResponse updateOrder(String orderId, OrderForm form);
    OrderResponse getOrderById(String orderId);
    List<OrderResponse> getOrderByUserId(Long userId);
//    OrderResponse getAllOrders();
}
