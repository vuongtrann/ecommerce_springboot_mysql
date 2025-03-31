package com.ecommerce.ecommercespringbootmysql.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"parent", "children","products"})  // Loại bỏ cả hai thuộc tính
public class Category extends BaseEntity {
    private String name;
    private String slug;

    //Quan hệ many-to-one : một category có thể có nhiểu category cha
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent;

    // Quan hệ One-to-Many: Một category có thể có nhiều category con
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>();


    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private List<Product> products;

    public Category(String name, Category parent, List<Category> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

}
