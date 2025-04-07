package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.model.dao.response.projection.CollectionProjection;
import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.utils.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String slug;
    private String primaryImageURL;
    private List<String> imageURLs;
    private String sku;
    private int quantity;
    private int quantityAvailable;
    private int soldQuantity;
    private double originalPrice; //gia goc
    private double sellingPrice; //gia ban
    private double discountedPrice; //gia giam
    private int noOfView;
    private String sellingType;
    private double avgRating;
    private int noOfRating;
    private Status status;
    private Boolean hasVariants;
    private Long createdAt;
    private Long updatedAt;

    private List<CategoryResponse> categories;
    private List<TagProjection> tags;
    private List<CollectionProjection> collections;
    private List<String> variants;
    private List<String> images;
}
