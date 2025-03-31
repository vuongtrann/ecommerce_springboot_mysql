package com.ecommerce.ecommercespringbootmysql.model.dao.request.Variant;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariantForm {
    private String sku;
    private int quantityAvailable;
    private double price;
    private double salePrice;
    private List<String> imageURLs;
    private List<VariantOptionForm> variantOptions;
}
