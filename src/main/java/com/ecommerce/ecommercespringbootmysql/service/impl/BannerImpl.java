package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Banner;
import com.ecommerce.ecommercespringbootmysql.repository.BannerRepository;
import com.ecommerce.ecommercespringbootmysql.service.BannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BannerImpl implements BannerService {
    private final BannerRepository bannerRepository;;


    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner findById(String id) {
        return bannerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner createBanner(BannerForm bannerForm) {
        Banner banner = new Banner(
          bannerForm.getTitle(),
                bannerForm.getDescription()
        );
        Banner savedBanner = bannerRepository.save(banner);
        return savedBanner;
    }

    @Override
    public void deleteBanner(String id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        bannerRepository.deleteById(id);
    }

    @Override
    public Banner updateBanner(String id,BannerForm bannerForm) {
       Banner banner = bannerRepository.findById(id).orElse(null);
       banner.setTitle(bannerForm.getTitle());
       banner.setDescription(bannerForm.getDescription());
       Banner savedBanner = bannerRepository.save(banner);
       return savedBanner;
    }
}
