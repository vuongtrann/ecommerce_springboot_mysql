package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.utils.Enum.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserId(Long userId);
    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
