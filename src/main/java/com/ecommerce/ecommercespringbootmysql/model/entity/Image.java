package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.ecommerce.ecommercespringbootmysql.model.entity.Variant.ProductVariant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "images")
public class Image{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;


    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = true)
    private ProductVariant productVariant;
}
