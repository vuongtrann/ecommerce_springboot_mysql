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

@Entity(name = "collection")
@Table(name = "collection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collection extends BaseEntity{
    private String collectionName;
    private String collectionDescription;
    private String collectionImage;
    private String slug;

    @ManyToMany(mappedBy = "collections")
    @JsonBackReference
    private List<Product> products;
}
