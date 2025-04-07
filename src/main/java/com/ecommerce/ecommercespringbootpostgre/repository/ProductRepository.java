package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.ProductProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Product;
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
