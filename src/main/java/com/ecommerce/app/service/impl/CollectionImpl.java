package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CollectionForm;
import com.ecommerce.app.model.dao.response.projection.CollectionProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.repository.CollectionRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.CollectionService;
import com.ecommerce.app.service.utils.SlugifyService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
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
    private final ProductRepository productRepository;

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
    public List<Collection> findByIdIn(List<String> ids) {
        return collectionRepository.findAllByIdIn(ids);
    }

    @Override
    public Page<CollectionProjection> getAllCollections(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
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
        collection.setSlug(slugify.generateSlug(collectionForm.getCollectionSlug()));
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }

    @Override
    public void deleteCollection(String id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        if (collection.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.COLLECTION_CANNOT_DELETE);
        }
        collection.setStatus(Status.DELETED);
        collectionRepository.save(collection);
    }

    @Override
    public void changeStatus(String id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        if (collection.getStatus() == Status.ACTIVE) {
            collection.setStatus(Status.INACTIVE);
        } else {
            collection.setStatus(Status.ACTIVE);
        }
        collectionRepository.save(collection);
    }


    @Override
    public void addCollectionToProduct(String productId, String collectionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->  new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() ->  new AppException(ErrorCode.COLLECTION_NOT_FOUND));

// Tránh add trùng
        if (!product.getCollections().contains(collection)) {
            product.getCollections().add(collection);
            productRepository.save(product);
        }
    }
}
