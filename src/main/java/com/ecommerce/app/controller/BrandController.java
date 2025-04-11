package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.BrandForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.projection.BrandProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.service.BrandService;
import com.ecommerce.app.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BrandController {
    private final BrandService brandService;


    @GetMapping
    public ResponseEntity<Page<BrandProjection>> getAllBrand(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(brandService.getAllBrands( page, size, sortBy, direction));
    }

    @PostMapping
    public ResponseEntity<AppResponse<Brand>> addBrand(@RequestBody BrandForm brandForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        brandService.createBrand(brandForm)
                )
        );
    }


    @DeleteMapping("/{brandId}")
    public ResponseEntity<AppResponse<String>> deleteBrand(@PathVariable String brandId) {

        brandService.deleteBrand(brandId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        brandId
                )
        );
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<AppResponse<Brand>> updateBrand(@PathVariable String brandId, @RequestBody BrandForm brandForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        brandService.updateBrand(brandId,brandForm)
                )
        );
    }

    @PutMapping("/{brandId}/status")
    public ResponseEntity<AppResponse<String>> changeStatus(@PathVariable String brandId) {
        brandService.changeStatus(brandId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Changed status successfully !"
                )
        );
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<AppResponse<String>>addBrandToProduct(
            @PathVariable String productId,
            @RequestBody Map<String, String> body) {

        String brandId = body.get("id"); // key là "id" như trong postman cậu gửi
        brandService.addBrandToProduct(productId, brandId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                SuccessCode.ADD_BRAND_PRODUCT,
                        "Check productId: " + productId
        ));
    }

}
