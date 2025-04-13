package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.Variant.ProductVariant;
import com.ecommerce.app.repository.ImageRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.CloudinaryService;
import com.ecommerce.app.service.ImageService;
import com.ecommerce.app.utils.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

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


    @Override
    @Transactional
    public void deleteImageByProductIdAndUrl(String imageUrl) {
        // Tìm ảnh theo URL
        Image image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeException("Ảnh không tồn tại trong database"));

        Product product = image.getProduct();
        if (product != null) {
            // Xoá trên Cloudinary
            cloudinaryService.deleteImageByUrl(imageUrl);

            //Xóa trong database
            // Kiểm tra nếu ảnh xóa trùng với primaryImageURL của product
            if (imageUrl.equals(product.getPrimaryImageURL())) {

                product.getImages().remove(image);
                Image newPrimaryImage = product.getImages().stream()
                        .findFirst() // Lấy ảnh đầu tiên còn lại trong danh sách
                        .orElseThrow(() -> new RuntimeException("Không có ảnh còn lại để cập nhật primary image"));
                product.setPrimaryImageURL(newPrimaryImage.getUrl()); // Cập nhật primaryImageURL cho product
                imageRepository.save(newPrimaryImage); // Cập nhật ảnh mới làm primary
            } else {
                product.getImages().remove(image);
            }

            imageRepository.delete(image);
            productRepository.save(product);
        }
    }


    @Override
    @Transactional
    public void deleteImageByVariantIdAndUrl(String imageUrl) {

        // Tìm ảnh theo URL
        Image image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeException("Ảnh không tồn tại trong database"));

        // Xoá trên Cloudinary
        cloudinaryService.deleteImageByUrl(imageUrl);

        // Xoá trong database
        ProductVariant variant = image.getProductVariant();
        if(variant != null) {
            variant.getImages().remove(image);
        }

        imageRepository.delete(image);
    }
}


