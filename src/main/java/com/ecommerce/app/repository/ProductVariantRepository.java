package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Variant.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
}
