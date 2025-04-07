package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.TagProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
//    @Query("select t from tag t where t.status = 'ACTIVE'")
    Page<TagProjection> findAllProjectedBy(Pageable pageable);

    boolean existsByTagName(String tagName);
}
