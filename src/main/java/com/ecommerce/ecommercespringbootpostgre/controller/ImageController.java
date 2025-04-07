package com.ecommerce.ecommercespringbootpostgre.controller;

import com.ecommerce.ecommercespringbootpostgre.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootpostgre.service.ImageService;
import com.ecommerce.ecommercespringbootpostgre.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
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
                        SuccessCode.CREATED_IMAGE_PRODUCT,
                        imageService.addImagesToProduct(productId, urls)
                )
        );
    }
}
