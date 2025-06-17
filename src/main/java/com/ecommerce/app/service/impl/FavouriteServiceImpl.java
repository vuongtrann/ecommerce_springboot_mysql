package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.response.dto.FavouriteResponse;
import com.ecommerce.app.model.entity.Favourite;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.model.mapper.FavouriteMapper;
import com.ecommerce.app.repository.FavouriteRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.FavouriteService;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepositiory userRepository;
    private final ProductRepository productRepository;

    @Override
    public void toggleFavourite(Long userUid, String productId) {
        Optional<Favourite> optional = favouriteRepository.findByUser_UIDAndProduct_Id(userUid, productId);
        if (optional.isPresent()) {
            favouriteRepository.delete(optional.get()); // Bỏ yêu thích
        } else {
            Favourite favourite = new Favourite();
            favourite.setUser(userRepository.findByUID(userUid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
            favourite.setProduct(productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
            favouriteRepository.save(favourite); // Thêm yêu thích
        }
    }

    @Override
    public FavouriteResponse getFavouriteByUser(Long userUid) {
        List<Favourite> favourites = favouriteRepository.findByUser_UID(userUid);
        return FavouriteMapper.toResponse(userUid, favourites);
    }


}
