package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findBySlug(String slug);

    boolean existsByName(String name);

    List<Category> findAllByIdIn(List<String> ids);

    List<String> id(String id);
}
