package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Banner;

import java.util.List;

public interface BannerService {
    Banner save(Banner banner);
    Banner findById(String id);
    List<Banner> getAllBanners();
    Banner createBanner(BannerForm bannerForm);
    Banner updateBanner(String id,BannerForm bannerForm);
    void deleteBanner(String id);

}
