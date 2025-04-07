package com.ecommerce.ecommercespringbootpostgre.model.entity.Variant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "variant_option")
@Table(name = "variant_option")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariantOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "variant_type_id")
    private VariantType variantType;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    @JsonIgnore
    private ProductVariant productVariant;
}
