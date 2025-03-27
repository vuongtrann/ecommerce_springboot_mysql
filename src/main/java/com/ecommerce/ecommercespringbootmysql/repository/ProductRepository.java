package com.ecommerce.ecommercespringbootmysql.repository;

import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findProductBySlug(String slug);
}
