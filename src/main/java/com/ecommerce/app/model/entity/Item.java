package com.ecommerce.app.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Auditable;

@Entity(name = "Item")
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
    private double unitPrice;

    public double getTotalPrice(){
        return unitPrice * quantity;
    }

}
