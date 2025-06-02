package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.request.OrderForm;
import com.ecommerce.app.model.dao.response.dto.ItemResponse;
import com.ecommerce.app.model.dao.response.dto.OrderResponse;
import com.ecommerce.app.model.entity.Item;
import com.ecommerce.app.model.entity.Order;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.Shipping;
import com.ecommerce.app.utils.Enum.PayStatus;
import com.ecommerce.app.utils.Enum.PayType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order toEntity(OrderForm form, List<Item> items) {
        Order order = new Order();
        order.setUserId(form.getUserId());
        order.setCardId(form.getCardId());
        order.setItems(items);
        order.setTotalPrice(items.stream().mapToDouble(Item::getTotalPrice).sum() + form.getShippingFee());
        order.setStatus(PayStatus.PENDING);
        order.setPayType(form.getPayType());
        order.setOrderDate(System.currentTimeMillis());
        order.setShippingFee(form.getShippingFee());

//        Shipping shipping = new Shipping();
//        shipping.setAddress(request.getShipping().getAddress());
//        shipping.setDeliveryDate(request.getShipping().getDeliveryDate());
//        shipping.setOrder(order); // mappedBy="order"
//        order.setShipping(shipping);

        order.setOrderDate(Instant.now().toEpochMilli());
        order.setCreatedAt(Instant.now().toEpochMilli());
        order.setUpdatedAt(Instant.now().toEpochMilli());
        order.setCreatedBy("system");
        order.setUpdatedBy("system");
        return order;
    }

    public OrderResponse toResponse(Order order) {
        List<ItemResponse> itemResponses = order.getItems().stream().map(item -> {
            Product product = item.getProduct();
            return new ItemResponse(
                    item.getId(),
                    product.getId(),
                    product.getName(),
                    product.getPrimaryImageURL(),
                    item.getQuantity(),
                    item.getUnitPrice()
            );
        }).collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getCardId(),
                itemResponses,
                order.getTotalPrice(),
                order.getStatus(),
                order.getPayType(),
                order.getOrderDate(),
                order.getShippingFee(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCreatedBy(),
                order.getUpdatedBy()
        );
    }
}
