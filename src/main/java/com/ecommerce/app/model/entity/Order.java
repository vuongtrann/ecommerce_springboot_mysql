package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Enum.OrderStatus;
import com.ecommerce.app.utils.Enum.PayStatus;
import com.ecommerce.app.utils.Enum.PayType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "oder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntityForBuying {
    private Long userId;
    private String cardId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // foreign key trong bảng order
    private User user;

    private double totalPrice;
    private PayStatus payStatus;
    private PayType payType;
    private Long orderDate;
    private double shippingFee;

    private OrderStatus orderStatus;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Shipping shipping;
}
