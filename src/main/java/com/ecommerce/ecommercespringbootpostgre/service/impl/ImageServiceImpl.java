package com.ecommerce.ecommercespringbootpostgre.service.impl;

import com.ecommerce.ecommercespringbootpostgre.exception.AppException;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Image;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Product;
import com.ecommerce.ecommercespringbootpostgre.repository.ImageRepository;
import com.ecommerce.ecommercespringbootpostgre.repository.ProductRepository;
import com.ecommerce.ecommercespringbootpostgre.service.ImageService;
import com.ecommerce.ecommercespringbootpostgre.utils.ErrorCode;
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
