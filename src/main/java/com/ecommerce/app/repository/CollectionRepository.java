package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.CollectionProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, String> {
    Page<CollectionProjection> findAllCollectionBy(Pageable pageable);

    Optional<Collection> findCollectionBySlug(String slug);

    List<Collection> findAllByIdIn(List<String> ids);
}

