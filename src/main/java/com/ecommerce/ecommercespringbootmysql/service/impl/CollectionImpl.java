package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.CollectionForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Collection;
import com.ecommerce.ecommercespringbootmysql.repository.CollectionRepository;
import com.ecommerce.ecommercespringbootmysql.service.CollectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionImpl implements CollectionService {
    private final CollectionRepository collectionRepository;

    @Override
    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public Collection findById(String id) {
        return collectionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    @Override
    public Collection createCollection(CollectionForm collectionForm) {
        Collection collection = new Collection(
                collectionForm.getCollectionName(),
                collectionForm.getCollectionDescription(),
                collectionForm.getCollectionImage(),
                collectionForm.getCollectionSlug()
        );
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }

    @Override
    public Collection updateCollection(String id, CollectionForm collectionForm) {
        Collection collection = collectionRepository.findById(id).orElse(null);
        collection.setCollectionName(collectionForm.getCollectionName());
        collection.setCollectionDescription(collectionForm.getCollectionDescription());
        collection.setCollectionImage(collectionForm.getCollectionImage());
        collection.setCollectionSlug(collectionForm.getCollectionSlug());
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }

    @Override
    public void deleteCollection(String id) {
        Collection collection = collectionRepository.findById(id).orElse(null);
        collectionRepository.delete(collection);
    }
}
