package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CategoryForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.entity.Category;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.mapper.CategoryMapper;
import com.ecommerce.app.model.mapper.ProductMapper;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.service.CategoryService;
import com.ecommerce.app.service.utils.SlugifyService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Cacheable(value = "CATEGORY_BY_ID", key = "#id")

    public CategoryResponse findById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        CategoryResponse categoryResponse = CategoryMapper.toCategoryResponse(category);
        return categoryResponse;
    }

    @Override
    public List<Category> findByIdIn(List<String> ids) {
        return categoryRepository.findAllByIdIn(ids);
    }

    @Override
    @Cacheable(value = "CATEGORY_BY_SLUG", key = "#slug")

    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        CategoryResponse categoryResponse = CategoryMapper.toCategoryResponse(category);
        return categoryResponse;
    }

    @Override
    public Category create(CategoryForm form) {
        boolean existsCategory = categoryRepository.existsByName(form.getName());
        if (existsCategory) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        // Tìm parent category (nếu có)
        Category parentCategory = (form.getParentId() != null)
                ? categoryRepository.findById(form.getParentId()).orElse(null)
                : null;

        // Tìm danh sách child categories (nếu có)
        List<Category> childCategories = form.getChildId() != null
                ? categoryRepository.findAllById(form.getChildId())
                : new ArrayList<>();

        Category category = new Category(form.getName(),parentCategory,childCategories);
        category.setSlug(slugify.generateSlug(form.getName()));

        category.setCreatedAt(Instant.now().toEpochMilli());
        category.setUpdatedAt(Instant.now().toEpochMilli());

        if (parentCategory != null) {
            parentCategory.getChildren().add(category);
        }
        for (Category childCategory : childCategories) {
            childCategory.setParent(category);
        }

        categoryRepository.save(category);


        return category;
    }

    @Override
    @Caching(put = {
            @CachePut(value = "CATEGORY_BY_ID", key = "#id"),
            @CachePut (value = "CATEGORY_BY_SLUG", key = "#form.slug")
    })
    public Category update(String id,CategoryForm form) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        boolean existsCategory = categoryRepository.existsByName(form.getName());

        if (existsCategory) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }


        // Tìm parent category (nếu có)
        Category parentCategory = (form.getParentId() != null)
                ? categoryRepository.findById(form.getParentId()).orElse(null)
                : null;

        // Tìm danh sách child categories (nếu có)
        List<Category> childCategories = form.getChildId() != null
                ? categoryRepository.findAllById(form.getChildId())
                : new ArrayList<>();

        category.setParent(parentCategory);
        category.setChildren(childCategories);

        if (parentCategory != null) {
            parentCategory.getChildren().add(category);
        }
        for (Category childCategory : childCategories) {
            childCategory.setParent(category);
        }

        category.setName(form.getName());
        category.setSlug(slugify.generateSlug(form.getName()));
        category.setUpdatedAt(Instant.now().toEpochMilli());

        categoryRepository.save(category);

        return category;
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
            @CacheEvict(value = "CATEGORY_BY_SLUG", key = "#slug")
    })
    public void delete(String id) {
         Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
         /*** TODO
          * 1. Xóa category khỏi danh sách children của parent category
          * 2. Xóa category khỏi danh sách parent của children categories
          * 3. Xóa category
          * 4. Kiem tra xem category có sản phẩm không, nếu có thì không xóa
          */

         categoryRepository.delete(category);
    }

}
