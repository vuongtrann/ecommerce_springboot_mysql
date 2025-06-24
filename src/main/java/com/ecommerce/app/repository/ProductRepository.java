package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @EntityGraph(attributePaths = {"images"})
    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Optional<Product> findProductBySlug(String slug);

    @Query("SELECT p FROM product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.slug) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProductByNameOrSlug(String keyword);

    @Query("SELECT p.name,f from product p join favourites f on (p.id = f.id)")

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findBySlug(String slug);

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    boolean existsByName(String name);
    boolean existsBySlug(String slug);

}
