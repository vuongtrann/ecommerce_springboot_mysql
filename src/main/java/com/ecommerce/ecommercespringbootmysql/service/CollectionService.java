package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.CollectionForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Collection;

import java.util.List;

public interface CollectionService {
    Collection saveCollection(Collection collection);
    Collection findById(String id);
    List<Collection> getAllCollections();
    Collection createCollection(CollectionForm collectionForm);
    Collection updateCollection(String id,CollectionForm collectionForm);
    void deleteCollection(String id);

}
