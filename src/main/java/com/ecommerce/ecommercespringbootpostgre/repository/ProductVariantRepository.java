package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.entity.Variant.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
}
