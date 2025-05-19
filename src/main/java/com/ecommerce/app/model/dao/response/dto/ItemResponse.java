package com.ecommerce.app.model.dao.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double unitPrice;
}
