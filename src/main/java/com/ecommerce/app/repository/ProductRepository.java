package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.dao.response.projection.ProductWithAvgRatingProjection;
import com.ecommerce.app.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Optional<Product> findProductBySlug(String slug);

    @Query("SELECT p FROM product p " +
            "WHERE (p.sellingPrice BETWEEN :keywordInt1 AND :keywordInt1) " +
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProductByNameOrSlug(Double keywordInt1,String keyword);




    @Query("SELECT p FROM product p WHERE p.sellingPrice between (:keyword) and (:keyword1)")
    List<Product> searchProductByPrice(Double keyword, Double keyword1);

    @Query(
            value = "SELECT " +
                    "p.id AS productId, " +
                    "p.name AS productName, " +
                    "p.description AS productDescription, " +
                    "p.primary_imageurl AS primaryImageUrl, " +
                    "p.selling_price AS sellingPrice, " +
                    "COALESCE(AVG(c.rating), 0) AS avgRating " +
                    "FROM product p " +
                    "LEFT JOIN comments c ON p.id = c.product_id " +
                    "GROUP BY p.id " +
                    "ORDER BY avgRating DESC",
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM product p",
            nativeQuery = true
    )
    Page<ProductWithAvgRatingProjection> findAllWithAvgRating(Pageable pageable);





    Page<Product> findAll(Pageable pageable);

    Optional<Product> findBySlug(String slug);

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    boolean existsByName(String name);
    boolean existsBySlug(String slug);

}
