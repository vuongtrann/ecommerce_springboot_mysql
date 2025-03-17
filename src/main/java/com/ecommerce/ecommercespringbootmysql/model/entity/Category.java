package com.ecommerce.ecommercespringbootmysql.model.entity;

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
public class Category extends BaseEntity {
    private String name;
    private String slug;

    //Quan hệ many-to-one : một category có thể có nhiểu category cha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // Quan hệ One-to-Many: Một category có thể có nhiều category con
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();


    public Category(String name, Category parent, List<Category> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

}
