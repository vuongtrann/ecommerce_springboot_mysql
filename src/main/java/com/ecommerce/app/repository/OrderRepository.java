package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.dto.OrderResponseADM;
import com.ecommerce.app.model.dao.response.projection.OrderStatisticProjection;
import com.ecommerce.app.model.dao.response.projection.SimpleUserProjectionForCountOrder;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.utils.Enum.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserUid(Long userUid);
    List<Order> findAllByUserUidAndOrderStatus(Long userUid, OrderStatus orderStatus);

    @Query(value = "SELECT u.uid AS userUid, u.first_name AS firstName, u.last_name AS lastName, " +
            "u.email AS email, u.avatar AS avatar, u.phone AS phone, COUNT(*) AS totalOrder " +
            "FROM oder o " +
            "JOIN users u ON o.user_order_id = u.id " +
            "WHERE u.uid IS NOT NULL " +
            "GROUP BY u.uid, u.first_name, u.last_name, u.email, u.avatar, u.phone",
            nativeQuery = true)
    List<SimpleUserProjectionForCountOrder> countTotalOrderByUser();


}
