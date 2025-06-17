package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.response.dto.FavouriteResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.entity.Favourite;

import java.util.List;
import java.util.stream.Collectors;

public class FavouriteMapper {
    public static FavouriteResponse toResponse(Long userUid, List<Favourite> favourites) {
        List<ProductResponse> productResponses = favourites.stream()
                .map(f -> ProductMapper.toResponse(f.getProduct()))
                .collect(Collectors.toList());

        String favouriteId = favourites.isEmpty() ? null : favourites.get(0).getId();

        return FavouriteResponse.builder()
                .favouriteId(favouriteId)
                .userUid(userUid)
                .products(productResponses)
                .build();
    }
}