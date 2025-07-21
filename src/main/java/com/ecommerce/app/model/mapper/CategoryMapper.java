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

        return Category.builder()
                .name(request.getName()!= null ? request.getName() : null)
                .status(request.getStatus())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .status(category.getStatus())
                .build();
    }

}
