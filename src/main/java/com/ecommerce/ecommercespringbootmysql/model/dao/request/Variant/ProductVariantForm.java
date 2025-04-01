package com.ecommerce.ecommercespringbootmysql.model.dao.request.Variant;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariantForm {
    private String sku;
    private int quantityAvailable;

    private double sellingPrice; //gia ban
    private double originalPrice; //gia goc
    private double discountedPrice=0; //gia giam
    private List<String> imageURLs;
    private List<VariantOptionForm> variantOptions;
}
