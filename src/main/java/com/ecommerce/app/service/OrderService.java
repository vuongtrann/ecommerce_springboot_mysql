package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.utils.Enum.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderForm form);
    OrderResponse updateOrder(String orderId, OrderForm form);
    OrderResponse getOrderById(String orderId);
    List<OrderResponse> getOrderByUserIdAndStatus(Long userId, OrderStatus orderStatus);
    OrderResponse updateOrderStatus(Long userId,String orderId, OrderStatus orderStatus);
//    OrderResponse getAllOrders();
}
