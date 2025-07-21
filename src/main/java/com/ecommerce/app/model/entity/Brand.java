package com.ecommerce.app.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

}
