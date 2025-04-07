package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.entity.Variant.VariantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantTypeRepository extends JpaRepository<VariantType, Long> {
}
