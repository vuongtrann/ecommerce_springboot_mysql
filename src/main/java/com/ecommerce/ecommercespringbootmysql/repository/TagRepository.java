package com.ecommerce.ecommercespringbootmysql.repository;

import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.TagProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, String> {
//    @Query("select t from tag t where t.status = 'ACTIVE'")
    Page<TagProjection> findAllProjectedBy(Pageable pageable);

    boolean existsByTagName(String tagName);
}
