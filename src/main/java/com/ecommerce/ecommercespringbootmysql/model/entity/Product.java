package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private String description;
    private String slug;
    private String primaryImageURL;
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageURLs;
    private String sku;
    private int quantity;
    private int quantityAvailable;
    private int soldQuantity = 0;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Product( String name, String description, String slug, String sku, int quantity,  double originalPrice,  double sellingPrice,  double discountedPrice, String sellingType, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.sku = sku;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.discountedPrice = discountedPrice;
        this.sellingType = sellingType;
        this.categories = categories;
    }
}
