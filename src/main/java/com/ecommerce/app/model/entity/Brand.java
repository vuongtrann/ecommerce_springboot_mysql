package com.ecommerce.app.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "brand")
@Table(name = "brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntity{
    private String name;
    private String description;
    private String slug;

    @ManyToMany(mappedBy = "brands")
    @JsonBackReference
    private List<Product> products;

}
