package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.ProductForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.Variant.VariantType;
import com.ecommerce.app.service.ProductSerice;
import com.ecommerce.app.utils.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    private final ProductSerice productService;


    @GetMapping
    public ResponseEntity<Page<ProductProjection>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(productService.findAll( page, size, sortBy, direction));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<AppResponse<ProductResponse>> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        productService.getProductById(productId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<AppResponse<ProductResponse>> addProduct(@RequestBody ProductForm productForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        productService.create(productForm)
                )
        );
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<AppResponse<String>> deleteProduct(@PathVariable String productId) {

        productService.delete(productId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        productId
                )
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<AppResponse<Product>> updateProduct(@PathVariable String productId, @RequestBody ProductForm productForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        productService.update(productId,productForm)
                )
        );
    }

    @PutMapping("/{productId}/status")
    public ResponseEntity<AppResponse<String>> changeStatus(@PathVariable String productId) {
        productService.changeStatus(productId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Changed status successfully !"
                )
        );
    }





    /**Variant Type*/
    @GetMapping("/variant-type")
    public ResponseEntity<AppResponse<List<VariantType>>> getVariantTypes() {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        productService.getVariantTypes()
                )
        );
    }
    @PostMapping("/variant-type")
    public ResponseEntity<AppResponse<VariantType>> createVariantType(@RequestBody VariantType variantType) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        productService.createVariantType(variantType)
                )
        );
    }
}
