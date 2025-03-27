package com.ecommerce.ecommercespringbootmysql.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity(name = "tag")
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag  extends BaseEntity {
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
