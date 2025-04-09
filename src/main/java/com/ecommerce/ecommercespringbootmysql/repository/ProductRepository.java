package com.ecommerce.ecommercespringbootmysql.repository;

import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.ProductProjection;

import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT * FROM product " +
            "WHERE LOWER(name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', ?1, '%'))",
            nativeQuery = true)
    List<Product> searchByNameOrDescription(String keyword);

    @Query(value = "SELECT p.* FROM product p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "WHERE pc.category_id = :categoryId", nativeQuery = true)
    List<Product> findByCategoryId(@Param("categoryId") String categoryId);



    @EntityGraph(attributePaths = {"images"})
    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Optional<Product> findProductBySlug(String slug);


}
