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
    private double rating;
    private int quantityAvailable;
    private int soldQuantity;
    private double price;
    private double salePrice;
    private double discountedPrice=0; //gia giam
    private String sku;
    private double mrsp;

    @ElementCollection
    private List<String> imageURLs;

    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JsonManagedReference
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantOption> variantOptions = new ArrayList<>();
}
