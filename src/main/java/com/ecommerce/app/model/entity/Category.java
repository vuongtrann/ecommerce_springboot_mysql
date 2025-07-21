package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Enum.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"parent", "children","products"})  // Loại bỏ cả hai thuộc tính
public class Category extends BaseEntity {
    private String name;
    private String slug;
    private Status status;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore // Hoặc dùng @JsonBackReference nếu muốn kiểm soát vòng lặp
    private List<Product> products;


    public Category(String name, Status status) {
        this.name = name;
        this.status = status;
    }

}
