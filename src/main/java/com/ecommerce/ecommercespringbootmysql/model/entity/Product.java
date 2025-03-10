package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private List<String> imagesURL;
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
}
