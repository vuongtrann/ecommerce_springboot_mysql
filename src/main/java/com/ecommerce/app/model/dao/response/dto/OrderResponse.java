package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.utils.Enum.OrderStatus;
import com.ecommerce.app.utils.Enum.PayStatus;
import com.ecommerce.app.utils.Enum.PayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private Long userId;
    private String cardId;
    private List<ItemResponse> items;
    private double totalPrice;
    private PayStatus payStatus;
    private PayType payType;
    private OrderStatus orderStatus;
    private Long orderDate;
    private double shippingFee;
//    private ShippingResponse shipping;

    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
}
