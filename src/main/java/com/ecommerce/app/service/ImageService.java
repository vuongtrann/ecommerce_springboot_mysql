package com.ecommerce.app.service;

import com.ecommerce.app.model.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<String> addImagesToProduct(String productId, List<String> urls);

    void deleteImageByProductIdAndUrl(String imageUrl);

    void deleteImageByVariantIdAndUrl(String imageUrl);


}
