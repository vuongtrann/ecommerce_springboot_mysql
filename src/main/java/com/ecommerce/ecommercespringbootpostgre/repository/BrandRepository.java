package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.BrandProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, String> {
    Page<BrandProjection> findAllBrandBy(Pageable pageable);

}
