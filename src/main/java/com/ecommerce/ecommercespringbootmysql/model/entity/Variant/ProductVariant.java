package com.ecommerce.ecommercespringbootmysql.model.entity.Variant;

import com.ecommerce.ecommercespringbootmysql.model.entity.BaseEntity;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "product_variant")
@Table(name = "product_variant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariant extends BaseEntity {
    private String name;
    private String description;
    private String slug;
    private String primaryImageURL;
    @ElementCollection
    private List<String> imageURLs;
    private String sku;
    private int quantity;

    private int quantityAvailable;
    private int soldQuantity;
    private double originalPrice; //gia goc
    private double sellingPrice; //gia ban
    private double discountedPrice=0; //gia giam
    private int noOfView;
    private String sellingType;
    private double avgRating;
    private int noOfRating;
//    private Boolean hasVariants = false



    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JsonManagedReference
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<VariantOption> variantOptions = new ArrayList<>();
}
