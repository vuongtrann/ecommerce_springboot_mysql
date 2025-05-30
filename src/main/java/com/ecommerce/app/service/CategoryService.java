package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CategoryForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.entity.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    List<Category> findAll();
    CategoryResponse findById(String id);
    List<Category> findByIdIn(List<String> ids);
    CategoryResponse findBySlug(String slug);
    Category create(CategoryForm form);
    Category update(String id, CategoryForm form);
    void setActivate(String id, boolean isActive);
    void delete(String id);
}
