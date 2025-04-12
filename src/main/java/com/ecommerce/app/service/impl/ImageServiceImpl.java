package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.ImageService;
import com.ecommerce.app.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public List<String> addImagesToProduct(String productId, List<String> urls) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->  new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<Image> images = urls.stream().map(url -> {
            Image image = new Image();
            image.setUrl(url);
            image.setProduct(product);
            return image;
        }).collect(Collectors.toList());
        product.getImages().addAll(images);
        productRepository.save(product);
        return product.getImages().stream().map(Image::getUrl).collect(Collectors.toList());
    }


}
