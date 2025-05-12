package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
//    @Query("select t from tag t where t.status = 'ACTIVE'")
    Page<TagProjection> findAllProjectedBy(Pageable pageable);

    List<Tag> findAllByIdIn(List<String> ids);
    boolean existsByTagName(String tagName);
}
