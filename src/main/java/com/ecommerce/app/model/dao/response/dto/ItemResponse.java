package com.ecommerce.app.model.dao.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    private String id;
    private String productId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double unitPrice;
}
