package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.response.dto.BrandResponse;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Category;

public class BrandMapper {
    public static BrandResponse toBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .slug(brand.getSlug())
                .status(brand.getStatus())
                .build();
    }
}
