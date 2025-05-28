package com.ecommerce.app.model.mapper;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CategoryForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.entity.Category;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final CategoryRepository categoryRepository;
    public Category toEntity (CategoryForm request) {
        List<Category> children = new ArrayList<>();
        if (request.getChildId() != null) {
            children.addAll(categoryRepository.findAllById(request.getChildId()));
        }
        return Category.builder()
                .name(request.getName()!= null ? request.getName() : null)
                .slug(request.getSlug()!= null ? request.getSlug() : null)
                .parent(request.getParentId() != null ? categoryRepository.findById(request.getParentId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND)) : null)
                .children(request.getChildId()!= null ? children : null)
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }

}
