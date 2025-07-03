package com.ecommerce.app.model.dao.request;

import lombok.Data;

import java.util.List;

@Data
public class CartForm {
    private Long userUid;
    private List<ItemForm> items;
}
