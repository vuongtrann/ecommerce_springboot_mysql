package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
//    @Query("select t from tag t where t.status = 'ACTIVE'")
    Page<TagProjection> findAllProjectedBy(Pageable pageable);

    boolean existsByTagName(String tagName);
}
