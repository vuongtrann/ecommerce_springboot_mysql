package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponseADM;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.model.mapper.OrderMapper;
import com.ecommerce.app.service.OrderService;
import com.ecommerce.app.service.VnPayService;
import com.ecommerce.app.utils.Enum.OrderStatus;
import com.ecommerce.app.utils.Enum.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    OrderMapper orderMapper;
    VnPayService vnPayService;

    @PostMapping("/create")
    public ResponseEntity<AppResponse<OrderResponse>> createOrder(@RequestBody OrderForm form) {
        OrderResponse orderResponse = orderService.createOrder(form);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CREATED,
                orderResponse
        ));
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<String> payOrder(@PathVariable String orderId, HttpServletRequest request) {
        Order order = orderService.findOrderById(orderId);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/api/v1/vnpay/vnpay-callback";
        String paymentUrl = vnPayService.createOrder(order, baseUrl);
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<OrderResponseADM>>> getAllOrder() {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FETCHED,
                orderService.getAllOrders()
        ));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<AppResponse<OrderResponse>> getOrderById(@PathVariable String orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FETCHED,
                orderResponse
        ));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<AppResponse<List<OrderResponse>>> getOrderByUserId(@PathVariable Long userId, @RequestParam(required = false) OrderStatus status) {
            List<OrderResponse> orderResponseList = orderService.getOrderByUserIdAndStatus(userId, status);
            return ResponseEntity.ok(AppResponse.builderResponse(
                    SuccessCode.FETCHED,
                    orderResponseList
            ));

    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<AppResponse<OrderResponse>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus orderStatus,
            @RequestParam Long userId) {
        OrderResponse updatedOrder = orderService.updateOrderStatus(userId, orderId, orderStatus);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.UPDATED,
                updatedOrder
        ));
    }



}
