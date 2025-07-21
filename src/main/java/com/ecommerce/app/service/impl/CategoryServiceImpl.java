package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CategoryForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.dao.response.projection.CategoryWithTotalProductProjection;
import com.ecommerce.app.model.dao.response.projection.ProductWithAvgRatingProjection;
import com.ecommerce.app.model.entity.Category;
import com.ecommerce.app.model.mapper.CategoryMapper;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.service.CategoryService;
import com.ecommerce.app.service.utils.SlugifyService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import com.ecommerce.app.utils.Enum.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    /***/
    CategoryRepository categoryRepository;
    SlugifyService slugify;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    @Override
    public Page<CategoryResponse> findAllCategoryWithTotalProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryWithTotalProductProjection> projections = categoryRepository.findAllCategoryWithTotalProduct(pageable);

        return projections.map(category -> {
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            System.out.println("Raw status from DB = " + category.getStatus());
            response.setStatus(Status.fromValue(category.getStatus()));
            response.setTotalProduct(category.getTotalProduct());
            return response;
        });
    }



    @Caching(evict = {
            @CacheEvict(value = "CATEGORY_BY_ID", key = "#id"),

    })
    @Override
    public void changeStatus(String id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        switch (category.getStatus()) {
            case ACTIVE -> category.setStatus(Status.INACTIVE);
            case INACTIVE -> category.setStatus(Status.ACTIVE);
            case DELETED -> throw new AppException(ErrorCode.CATEGORY_CANNOT_DELETE);
        }

        category.setUpdatedAt(Instant.now().toEpochMilli());
        categoryRepository.save(category);
    }




    @Override
    public List<Category> findByIdIn(List<String> ids) {
        return categoryRepository.findAllByIdIn(ids);
    }



    @Override
    public Category create(CategoryForm form) {
        boolean existsCategory = categoryRepository.existsByName(form.getName());
        if (existsCategory) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }


        Category category = new Category(form.getName(),form.getStatus());
        category.setSlug(slugify.generateSlug(form.getName()));


        category.setCreatedAt(Instant.now().toEpochMilli());
        category.setUpdatedAt(Instant.now().toEpochMilli());


        categoryRepository.save(category);


        return category;
    }

//    @Override
//    @Caching(put = {
//            @CachePut(value = "CATEGORY_BY_ID", key = "#id"),
//            @CachePut (value = "CATEGORY_BY_SLUG", key = "#result.slug")
//    })
//    public Category update(String id,CategoryForm form) {
//        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
//        boolean existsCategory = categoryRepository.existsByName(form.getName());
//
//        if (existsCategory) {
//            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
//        }
//
//
//        // Tìm parent category (nếu có)
//        Category parentCategory = (form.getParentId() != null)
//                ? categoryRepository.findById(form.getParentId()).orElse(null)
//                : null;
//
//        // Tìm danh sách child categories (nếu có)
//        List<Category> childCategories = form.getChildId() != null
//                ? categoryRepository.findAllById(form.getChildId())
//                : new ArrayList<>();
//
//        category.setParent(parentCategory);
//        category.setChildren(childCategories);
//
//        if (parentCategory != null) {
//            parentCategory.getChildren().add(category);
//        }
//        for (Category childCategory : childCategories) {
//            childCategory.setParent(category);
//        }
//
//        category.setName(form.getName());
//        category.setSlug(slugify.generateSlug(form.getName()));
//        category.setUpdatedAt(Instant.now().toEpochMilli());
//
//        categoryRepository.save(category);
//
//        return category;
//    }


    @Override
    @Cacheable(value = "CATEGORY_BY_SLUG", key = "#slug")

    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        CategoryResponse categoryResponse = CategoryMapper.toCategoryResponse(category);
        return categoryResponse;
    }


    @Override
    @Cacheable(value = "CATEGORY_BY_ID", key = "#id")

    public CategoryResponse findById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        CategoryResponse categoryResponse = CategoryMapper.toCategoryResponse(category);
        return categoryResponse;
    }

    @CachePut(value = "CATEGORY_BY_ID", key = "#id")
    @Override
    public CategoryResponse update(String id, CategoryForm form) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        boolean existsCategory = categoryRepository.existsByName(form.getName());
        if (existsCategory && !category.getName().equals(form.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        category.setName(form.getName());
        category.setStatus(form.getStatus());
        category.setSlug(slugify.generateSlug(form.getName()));
        category.setUpdatedAt(Instant.now().toEpochMilli());

        category = categoryRepository.save(category);
        CategoryResponse response = CategoryMapper.toCategoryResponse(category);

        return response; // <-- bây giờ object được cache chính là DTO, không dính Hibernate proxy
    }



    @Override
    public void setActivate(String id, boolean isActive) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setStatus(isActive ? Status.ACTIVE : Status.INACTIVE);
        category.setUpdatedAt(Instant.now().toEpochMilli());
        categoryRepository.save(category);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "CATEGORY_BY_ID", key = "#id"),
    })
    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));


        if (!category.getProducts().isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_IN_USE_BY_PRODUCT);
        }
        if (category.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.CATEGORY_CANNOT_DELETE);
        }
        category.setStatus(Status.DELETED);

        categoryRepository.save(category);
    }

}
