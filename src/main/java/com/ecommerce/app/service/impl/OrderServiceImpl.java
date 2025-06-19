package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponseADM;
import com.ecommerce.app.model.entity.Cart;
import com.ecommerce.app.model.entity.Item;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.model.mapper.OrderMapper;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.OrderService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import com.ecommerce.app.utils.Enum.OrderStatus;
import com.ecommerce.app.utils.Enum.PayStatus;
import com.ecommerce.app.utils.Enum.Role;
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
    private final UserRepositiory userRepositiory;

    @Override
    public List<OrderResponseADM> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return OrderMapper.toOrderListResponse(orders);
    }


    @Override
    public OrderResponse createOrder(OrderForm form) {
        User user = userRepositiory.findByUID(form.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
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

        Order order = orderMapper.toEntity(form, itemsToBuy,user);
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
    public Order findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return order;
    }

    @Override
    public List<OrderResponse> getOrderByUserIdAndStatus(Long userId, OrderStatus orderStatus) {
        List<Order> orders;
        if (orderStatus == null) {
            orders = orderRepository.findAllByUserId(userId);
        } else {
            orders = orderRepository.findAllByUserIdAndOrderStatus(userId, orderStatus);
        }

        if (orders.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(Long userId,String orderId, OrderStatus orderStatus) {
        User currentUser = userRepositiory.findByUID(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
//        if (currentUser.getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.UNAUTHORIZED);
//        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(orderStatus);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public void updateOrderPayStatus(String orderId, PayStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setPayStatus(status);
        orderRepository.save(order);
    }
}
