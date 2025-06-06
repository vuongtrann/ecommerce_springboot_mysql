package com.ecommerce.app.model.entity.Variant;

import com.ecommerce.app.model.entity.BaseEntity;
import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.model.entity.Product;
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
    private String sku;
    private int quantity;

    private int quantityAvailable;
    private int soldQuantity;
    private double originalPrice; //gia goc
    private double sellingPrice; //gia ban
    private double discountedPrice=0; //gia giam




    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JsonManagedReference
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL,orphanRemoval = true , fetch = FetchType.EAGER)
    private List<VariantOption> variantOptions = new ArrayList<>();

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
