package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.BrandProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Category;
import com.ecommerce.app.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, String> {
    Page<BrandProjection> findAllBrandBy(Pageable pageable);
    List<Brand> findAllByIdIn(List<String> ids);
}
