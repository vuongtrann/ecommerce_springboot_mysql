package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.BannerForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.entity.Banner;
import com.ecommerce.app.service.BannerService;
import com.ecommerce.app.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banner")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BannerController {
    /***TODO: Tiến dựa theo mấy cái controller trước làm cái này tương tự , làm xong hết xóa line này */
    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<AppResponse<List<Banner>>>  getAllBanners() {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                SuccessCode.FETCHED,
                bannerService.getAllBanners()
        )
        );
    }

    @PostMapping
    public ResponseEntity<AppResponse<Banner>> addBanner(@RequestBody BannerForm bannerForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        bannerService.createBanner(bannerForm)
                )
        );
    }


    @DeleteMapping("/{bannerId}")
    public ResponseEntity<AppResponse<String>> deleteBanner(@PathVariable String bannerId) {
       bannerService.deleteBanner(bannerId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        bannerId
                )
        );
    }

    @PutMapping("/{bannerId}")
    public ResponseEntity<AppResponse<Banner>> updateBanner(@PathVariable String bannerId, @RequestBody BannerForm bannerForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        bannerService.updateBanner(bannerId,bannerForm)
                )
        );
    }

}
