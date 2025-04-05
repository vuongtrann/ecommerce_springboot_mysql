package com.ecommerce.ecommercespringbootmysql.controller;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.BrandProjection;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.TagProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Banner;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import com.ecommerce.ecommercespringbootmysql.service.BrandService;
import com.ecommerce.ecommercespringbootmysql.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

}
