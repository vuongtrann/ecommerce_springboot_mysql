package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.mapper.OrderMapper;
import com.ecommerce.app.service.OrderService;
import com.ecommerce.app.utils.Enum.SuccessCode;
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

    @PostMapping("/create")
    public ResponseEntity<AppResponse<OrderResponse>> createOrder(@RequestBody OrderForm form) {
        OrderResponse orderResponse = orderService.createOrder(form);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CREATED,
                orderResponse
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
    public ResponseEntity<AppResponse<List<OrderResponse>>> getOrderByUserId(@PathVariable Long userId) {
        List<OrderResponse> orderResponseList = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FETCHED,
                orderResponseList
        ));
    }

}
