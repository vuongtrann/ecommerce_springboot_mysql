package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.dao.response.projection.CategoryWithTotalProductProjection;
import com.ecommerce.app.model.dao.response.projection.ProductWithAvgRatingProjection;
import com.ecommerce.app.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findBySlug(String slug);

    boolean existsByName(String name);

    List<Category> findAllByIdIn(List<String> ids);

    List<String> id(String id);

    @Query(
            value = "SELECT " +
                    "c.id, " +
                    "c.name, " +
                    "c.status, " +


                    "COUNT(p.id) AS totalProduct " +
                    "FROM category c " +
                    "LEFT JOIN product p ON p.category_id = c.id " +

                    "GROUP BY c.id, c.name, c.slug, c.status ",


            countQuery = "SELECT COUNT(DISTINCT c.name) FROM category c",
            nativeQuery = true
    )
    Page<CategoryWithTotalProductProjection> findAllCategoryWithTotalProduct(Pageable pageable);
}
