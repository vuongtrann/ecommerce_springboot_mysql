package com.ecommerce.app.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "oder")
public class Order extends BaseEntityForBuying {
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Item> items = new ArrayList<>();

    private double totalPrice;
    private String status;
    private String paymentMethod;
    private Long orderDate;
}
