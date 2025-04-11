package com.ecommerce.app.model.dao.request;

import com.ecommerce.app.model.dao.request.Variant.ProductVariantForm;
import com.ecommerce.app.model.entity.Image;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Original price is required")
    @Size(min = 1, message = "Original price must be greater than 0")
    private double originalPrice; //gia goc

    @NotBlank(message = "Selling price is required")
    @Size(min = 1, message = "Selling price must be greater than 0")
    private double sellingPrice; //gia ban

    @NotBlank(message = "Discounted price is required")
    @Size(min = 1, message = "Discounted price must be greater than 0")
    private double discountedPrice; //gia giam

//    private int noOfView;

    private String sellingType;

//    private double avgRating;

    private List<String> categories = new ArrayList<>();

    private List<String> brands = new ArrayList<>();

    private List<String> collections = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

//    private List<Image> images = new ArrayList<>();


//    private String brand ;
//
    private boolean hasVariants;
//
    private List<ProductVariantForm> variants = new ArrayList<>();

//
//    private List<String> collections = new ArrayList<>();
//
//    private Map<String, String> specifications ;

}
