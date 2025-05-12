package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Variant.VariantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantTypeRepository extends JpaRepository<VariantType, Long> {
}
