package com.ecommerce.ecommercespringbootpostgre.service;

import com.ecommerce.ecommercespringbootpostgre.model.dao.request.CollectionForm;
import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.CollectionProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CollectionService {
    Collection saveCollection(Collection collection);
    Optional<Collection> findById(String id);
    Page<CollectionProjection> getAllCollections(int page, int size, String sortBy, String direction);
    Collection createCollection(CollectionForm collectionForm);
    Collection updateCollection(String id,CollectionForm collectionForm);
    void deleteCollection(String id);
    void changeStatus(String id);
}
