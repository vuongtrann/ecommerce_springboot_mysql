package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Variant.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantOptionRepository extends JpaRepository<VariantOption, Long> {
}
