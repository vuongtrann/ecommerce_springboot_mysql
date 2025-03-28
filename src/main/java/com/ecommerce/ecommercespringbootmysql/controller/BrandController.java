package com.ecommerce.ecommercespringbootmysql.controller;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootmysql.model.entity.Banner;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import com.ecommerce.ecommercespringbootmysql.service.BrandService;
import com.ecommerce.ecommercespringbootmysql.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BrandController {
    /***TODO: Tiến dựa theo mấy cái controller trước làm cái này tương tự , làm xong hết xóa line này */
    private final BrandService brandService;


    @GetMapping
    public ResponseEntity<AppResponse<List<Brand>>> getAllBrands() {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        brandService.getAllBrands()
                )
        );
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
    public ResponseEntity<AppResponse<Brand>> deleteBrand(@PathVariable String brandId) {
        Brand brand = brandService.findById(brandId);
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        brand
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

}
