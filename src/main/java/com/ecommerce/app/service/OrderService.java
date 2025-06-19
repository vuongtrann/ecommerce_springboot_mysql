package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponseADM;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.utils.Enum.OrderStatus;
import com.ecommerce.app.utils.Enum.PayStatus;
import com.ecommerce.app.utils.Enum.PayType;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderForm form);
    OrderResponse updateOrder(String orderId, OrderForm form);
    OrderResponse getOrderById(String orderId);
    Order findOrderById(String orderId);
    List<OrderResponse> getOrderByUserIdAndStatus(Long userId, OrderStatus orderStatus);

    List<OrderResponseADM> getAllOrders();



    OrderResponse updateOrderStatus(Long userId,String orderId, OrderStatus orderStatus);
//    OrderResponse getAllOrders();
    void updateOrderPayStatus(String orderId, PayStatus status);
    void updateOrderWhenPaymentSuccess(String orderId, PayType payType, PayStatus payStatus);
}
