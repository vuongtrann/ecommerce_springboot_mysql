package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.request.ProductForm;
import com.ecommerce.app.model.dao.response.dto.CategoryResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.dao.response.dto.Variant.ProductVariantResponse;
import com.ecommerce.app.model.dao.response.dto.Variant.VariantOptionResponse;
import com.ecommerce.app.model.dao.response.dto.Variant.VariantTypeResponse;
import com.ecommerce.app.model.entity.Category;
import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public static Product toEntity(ProductForm request, List<Category> categories, List<Tag> tags) {
        return Product.builder()
                .name(request.getName() != null ? request.getName().trim() : null)
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .primaryImageURL(request.getPrimaryImageURL() != null ? request.getPrimaryImageURL().trim() : null)
                .sku(request.getSku() != null ? request.getSku().trim() : null)
                .quantity(request.getQuantity())
                .originalPrice(request.getOriginalPrice() != 0 ? request.getOriginalPrice() : null)
                .sellingPrice(request.getSellingPrice() != 0 ? request.getSellingPrice() : null)
                .discountedPrice(request.getDiscountedPrice() != 0 ? request.getDiscountedPrice() : null)
                .noOfView(0)
                .sellingType(request.getSellingType() != null ? request.getSellingType().trim() : null)
                .avgRating(0)
                .categories(categories)
                .tags(tags)
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        // Tạo ProductResponse mới từ Product entity
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .slug(product.getSlug())
                .primaryImageURL(product.getPrimaryImageURL())
                .sku(product.getSku())
                .quantity(product.getQuantity())
                .quantityAvailable(product.getQuantityAvailable())
                .soldQuantity(product.getSoldQuantity())
                .originalPrice(product.getOriginalPrice())
                .sellingPrice(product.getSellingPrice())
                .discountedPrice(product.getDiscountedPrice())
                .noOfView(product.getNoOfView())
                .sellingType(product.getSellingType())
                .avgRating(product.getAvgRating())
                .noOfRating(product.getNoOfRating())
                .hasVariants(product.getHasVariants())

                // Ánh xạ danh sách categories vào ProductResponse
                .categories(product.getCategories().stream()
                        .map(cat -> CategoryResponse.builder()
                                .id(cat.getId())
                                .name(cat.getName())
                                .build())
                        .collect(Collectors.toList())
                )

//                // Ánh xạ danh sách tags vào ProductResponse
//                .tags(product.getTags().stream()
//                        .map(tag -> TagResponse.builder()
//                                .id(tag.getId())
//                                .name(tag.getName())
//                                .build())
//                        .collect(Collectors.toList())
//                )
//
//                // Ánh xạ danh sách images vào ProductResponse
                .images(product.getImages() != null
                        ? product.getImages().stream()
                        .map(Image::getUrl)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
//
// ánh xạ variants
                .variants(product.getHasVariants() != null && product.getHasVariants() && product.getVariants() != null
                        ? product.getVariants().stream()
                        .map(variant -> ProductVariantResponse.builder()
                                .id(variant.getId())
                                .name(variant.getName())
                                .sku(variant.getSku())
                                .quantity(variant.getQuantity())
                                .quantityAvailable(variant.getQuantityAvailable())
                                .originalPrice(variant.getOriginalPrice())
                                .sellingPrice(variant.getSellingPrice())
                                .discountedPrice(variant.getDiscountedPrice())
                                .primaryImageURL(variant.getPrimaryImageURL())
                                .images(variant.getImages() != null
                                        ? variant.getImages().stream()
                                        .map(Image::getUrl)
                                        .collect(Collectors.toList())
                                        : new ArrayList<>())
                                .variantOptions(variant.getVariantOptions() != null
                                        ? variant.getVariantOptions().stream()
                                        .map(option -> VariantOptionResponse.builder()
                                                .id(option.getId())
                                                .value(option.getValue())
                                                .variantType(option.getVariantType() != null
                                                        ? VariantTypeResponse.builder()
                                                        .id(option.getVariantType().getId())
                                                        .type(option.getVariantType().getType())
                                                        .build()
                                                        : null)
                                                .build())
                                        .collect(Collectors.toList())
                                        : new ArrayList<>())
                                .build())
                        .collect(Collectors.toList())
                        : new ArrayList<>())

                .build();
    }


}
