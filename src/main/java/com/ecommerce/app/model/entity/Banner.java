package com.ecommerce.app.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "banner")
@Table(name = "banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banner extends BaseEntity {
    String title;
    String description;
    String imageURL;
    String position;

    public Banner(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
