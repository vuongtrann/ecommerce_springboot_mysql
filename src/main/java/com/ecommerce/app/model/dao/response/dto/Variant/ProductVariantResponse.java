package com.ecommerce.app.model.dao.response.dto.Variant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private String id;
    private String sku;
    private int quantity;
    private int quantityAvailable;
    private double originalPrice;
    private double sellingPrice;
    private double discountedPrice;
    private String primaryImageURL;
    private List<VariantOptionResponse> variantOptions;
    private List<String> images;
}
