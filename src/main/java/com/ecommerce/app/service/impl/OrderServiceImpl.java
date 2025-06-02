package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.entity.Cart;
import com.ecommerce.app.model.entity.Item;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.model.mapper.OrderMapper;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.service.OrderService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderForm form) {
        Cart cart = cartRepository.findByUserId(form.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        // Lọc các item cần mua theo ID
        List<Item> itemsToBuy = cart.getItems().stream()
                .filter(item -> form.getItemIdsToBuy().contains(item.getId()))
                .map(item -> new Item(
                        item.getProduct(),
                        item.getQuantity(),
                        item.getUnitPrice()
                ))
                .collect(Collectors.toList());
        if (itemsToBuy.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }

        Order order = orderMapper.toEntity(form, itemsToBuy);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);

    }

    @Override
    public OrderResponse updateOrder(String orderId, OrderForm form) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getOrderByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (orders.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
