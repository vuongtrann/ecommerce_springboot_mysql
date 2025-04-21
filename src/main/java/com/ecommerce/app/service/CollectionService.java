package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CollectionForm;
import com.ecommerce.app.model.dao.response.projection.CollectionProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CollectionService {
    Collection saveCollection(Collection collection);
    Collection findById(String id);
    Page<CollectionProjection> getAllCollections(int page, int size, String sortBy, String direction);
    Collection createCollection(CollectionForm collectionForm);
    Collection updateCollection(String id,CollectionForm collectionForm);
    void deleteCollection(String id);
    void changeStatus(String id);
    Collection findBySlug(String slug);

    List<Collection> findByIdIn(List<String> ids);
    void addCollectionToProduct(String productId, String collectionId);
}
