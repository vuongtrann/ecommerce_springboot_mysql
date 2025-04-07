package com.ecommerce.ecommercespringbootpostgre.service;

import com.ecommerce.ecommercespringbootpostgre.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Banner;

import java.util.List;
import java.util.Optional;

public interface BannerService {
    Banner save(Banner banner);
    Optional<Banner> findById(String id);
    List<Banner> getAllBanners();
    Banner createBanner(BannerForm bannerForm);
    Banner updateBanner(String id,BannerForm bannerForm);
    void deleteBanner(String id);

}
