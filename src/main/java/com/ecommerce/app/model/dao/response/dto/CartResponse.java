package com.ecommerce.app.model.dao.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String cartId;
    private Long userId;
    private List<ItemResponse> items;
}
