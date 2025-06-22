package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.response.dto.FavouriteResponse;
import com.ecommerce.app.model.entity.Favourite;

import java.util.List;

public interface FavouriteService {
    void toggleFavourite(Long userUid, String productId);
    FavouriteResponse getFavouriteByUser(Long userUid);
}
