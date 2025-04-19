package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.service.ImageService;
import com.ecommerce.app.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


    @PostMapping("/product/{productId}")
    public ResponseEntity<AppResponse<List<String>>> addImagesToProduct(@PathVariable String productId, @RequestBody List<String> urls) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.ADD_IMAGE_PRODUCT,
                        imageService.addImagesToProduct(productId, urls)
                )
        );
    }

    @DeleteMapping("/product/{productId}/delete-image-product")
    public ResponseEntity<AppResponse<String>> deleteImageByProductIdAndUrl(
            @RequestParam("url") String imageUrl) {

        imageService.deleteImageByProductIdAndUrl(imageUrl);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.DELETED,
                "Image Product deleted successfully"
        ));
    }

    @DeleteMapping("/product/{variantId}/delete-image-variant")
    public ResponseEntity<AppResponse<String>> deleteImageByVariantIdAndUrl(
            @RequestParam("url") String imageUrl) {

        imageService.deleteImageByVariantIdAndUrl(imageUrl);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.DELETED,
                "Image Product Variant deleted successfully"
        ));
    }




}
