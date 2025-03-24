package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseEntity {
    private String name;
    private String nameUnsigned;
    private String description;
    private String slug;
    private String primaryImageURL;
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageURLs;
    private String sku;
    private int quantityAvailable;
    private int soldQuantity;
    private double originalPrice; //gia goc
    private double sellingPrice; //gia ban
    private double discountedPrice; //gia giam
    private int noOfView;
    private String sellingType;
    private double avgRating;
    private int noOfRating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

}
