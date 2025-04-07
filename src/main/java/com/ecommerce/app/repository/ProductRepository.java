package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @EntityGraph(attributePaths = {"images"})
    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Optional<Product> findProductBySlug(String slug);
}
