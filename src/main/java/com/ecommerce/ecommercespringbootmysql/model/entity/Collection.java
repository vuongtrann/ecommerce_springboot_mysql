package com.ecommerce.ecommercespringbootmysql.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String collectionSlug;
}
