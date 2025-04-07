package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.CollectionProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, String> {
    Page<CollectionProjection> findAllCollectionBy(Pageable pageable);
}

