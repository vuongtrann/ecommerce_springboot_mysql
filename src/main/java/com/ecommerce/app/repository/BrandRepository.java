package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.BrandProjection;
import com.ecommerce.app.model.dao.response.projection.CategoryWithTotalProductProjection;
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

    Optional<Brand> findBrandBySlug(String slug);

    @Query(
            value = "SELECT " +
                    "b.id, " +
                    "b.name, " +
                    "b.status, " +


                    "COUNT(p.id) AS totalProduct " +
                    "FROM brand b " +
                    "LEFT JOIN product p ON p.brand_id = b.id " +

                    "GROUP BY b.id, b.name, b.status ",


            countQuery = "SELECT COUNT(DISTINCT b.name) FROM brand b",
            nativeQuery = true
    )
    Page<BrandProjection> findAllBrandWithTotalProduct(Pageable pageable);

}
