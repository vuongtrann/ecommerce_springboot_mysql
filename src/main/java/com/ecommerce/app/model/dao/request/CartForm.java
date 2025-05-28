package com.ecommerce.app.model.dao.request;

import lombok.Data;

import java.util.List;

@Data
public class CartForm {
    private Long userId;
    private List<ItemForm> items;
}
