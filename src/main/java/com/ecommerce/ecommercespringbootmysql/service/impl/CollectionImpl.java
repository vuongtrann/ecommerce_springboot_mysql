package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.CollectionForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.CollectionProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import com.ecommerce.ecommercespringbootmysql.model.entity.Collection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.ecommerce.ecommercespringbootmysql.repository.CollectionRepository;
import com.ecommerce.ecommercespringbootmysql.service.CollectionService;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    SlugifyService slugify;

    @Override
    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public Optional<Collection> findById(String id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.COLLECTION_NOT_FOUND));

        return Optional.of(collection);
    }

    @Override
    public Page<CollectionProjection> getAllCollections(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(sortBy, direction);
        if(direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else if(direction.equalsIgnoreCase("asc")) {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return collectionRepository.findAllCollectionBy(pageable);
    }

    @Override
    public Collection createCollection(CollectionForm collectionForm) {
        Collection collection = new Collection(
                collectionForm.getCollectionName(),
                collectionForm.getCollectionDescription(),
                collectionForm.getCollectionImage(),
                slugify.generateSlug(collectionForm.getCollectionSlug())
        );
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }

    @Override
    public Collection updateCollection(String id, CollectionForm collectionForm) {
        Collection collection = collectionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        collection.setCollectionName(collectionForm.getCollectionName());
        collection.setCollectionDescription(collectionForm.getCollectionDescription());
        collection.setCollectionImage(collectionForm.getCollectionImage());
        collection.setCollectionSlug(slugify.generateSlug(collectionForm.getCollectionSlug()));
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }

    @Override
    public void deleteCollection(String id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        if (!collection.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.COLLECTION_CANNOT_DELETE);
        }
        collection.setStatus(Status.DELETED);
        collectionRepository.save(collection);
    }
}
