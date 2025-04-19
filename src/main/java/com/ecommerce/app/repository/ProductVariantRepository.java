package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Variant.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
    @Modifying
    @Transactional
    void deleteByProductId(String productId);
}
