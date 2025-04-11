package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.TagForm;
import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface TagService {
    Page<TagProjection> findAll(int page, int size, String sortBy, String direction);
    Optional<Tag> findById(String id);
    Tag create(TagForm form);

    Tag update(String id , TagForm form);
    Tag save(Tag tag);
    void delete(String id);
    void changeStatus(String id);

    List<Tag> findByIdIn(List<String> ids);
    void addTagToProduct(String productId, String tagId);
}
