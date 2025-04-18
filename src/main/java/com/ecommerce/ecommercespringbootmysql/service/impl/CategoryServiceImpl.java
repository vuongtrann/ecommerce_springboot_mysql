package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.CategoryForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Category;
import com.ecommerce.ecommercespringbootmysql.repository.CategoryRepository;
import com.ecommerce.ecommercespringbootmysql.service.CategoryService;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findByIdIn(List<String> ids) {
        return categoryRepository.findAllByIdIn(ids);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return  categoryRepository.findBySlug(slug);
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
