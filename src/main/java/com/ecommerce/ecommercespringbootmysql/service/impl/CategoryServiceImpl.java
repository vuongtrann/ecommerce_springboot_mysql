package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.CategoryForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Category;
import com.ecommerce.ecommercespringbootmysql.repository.CategoryRepository;
import com.ecommerce.ecommercespringbootmysql.service.CategoryService;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SlugifyService slugify;

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
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
    public Category update(CategoryForm form) {
        return null;
    }

    private Category save(Category category) {
        return categoryRepository.save(category);
    }
}
