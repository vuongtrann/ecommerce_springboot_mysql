package com.ecommerce.app.model.dao.request;

import com.ecommerce.app.utils.Enum.PayType;
import lombok.Data;

import java.util.List;

@Data
public class OrderForm {
    private Long userId;
    private String cardId;
    private List<String> itemIdsToBuy; // chỉ mua 1 hoặc nhiều item từ cart
    private PayType payType;
    private double shippingFee;
//    private ShippingRequest shipping;
}
