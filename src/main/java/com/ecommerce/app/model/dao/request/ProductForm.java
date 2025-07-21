package com.ecommerce.app.model.dao.request;

import com.ecommerce.app.model.dao.request.Variant.ProductVariantForm;
import com.ecommerce.app.model.entity.Image;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@Data
public class ProductForm {
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;

    private String description;

    private String slug;

    private String primaryImageURL;

//    private List<String> imagesURL = new ArrayList<>();

    private String sku;

    private int quantity;

    @NotNull(message = "Original price is required")
    @DecimalMin(value = "0.01", message = "Original price must be greater than 0")
    private Double originalPrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.01", message = "Selling price must be greater than 0")
    private Double sellingPrice;

    @NotNull(message = "Discounted price is required")
    @DecimalMin(value = "0.0", message = "Discounted price must be >= 0")
    private Double discountedPrice;


//    private int noOfView;

    private String sellingType;

//    private double avgRating;
    @NotNull(message = "categoryId must not be null")
    private String categoryId;

    private String brandId;

    private List<String> collections = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

//    private List<Image> images = new ArrayList<>();

    private boolean hasVariants;
//
    private List<ProductVariantForm> variants = new ArrayList<>();

//    private Map<String, String> specifications ;

}
