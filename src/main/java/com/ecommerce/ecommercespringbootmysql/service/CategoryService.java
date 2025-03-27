package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.CategoryForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(String id);
    List<Category> findByIdIn(List<String> ids);
    Optional<Category> findBySlug(String slug);
    Category create(CategoryForm form);
    Category update(String id, CategoryForm form);
    void setActivate(String id, boolean isActive);
    void delete(String id);
}
