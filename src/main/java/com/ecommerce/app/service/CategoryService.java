package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CategoryForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.dao.response.projection.CategoryWithTotalProductProjection;
import com.ecommerce.app.model.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    List<Category> findAll();
    CategoryResponse findById(String id);
    List<Category> findByIdIn(List<String> ids);
    CategoryResponse findBySlug(String slug);
    Category create(CategoryForm form);
    CategoryResponse update(String id, CategoryForm form);
    Page<CategoryResponse> findAllCategoryWithTotalProduct(int size, int page);
    void setActivate(String id, boolean isActive);
    void changeStatus(String id);
    void delete(String id);
}
