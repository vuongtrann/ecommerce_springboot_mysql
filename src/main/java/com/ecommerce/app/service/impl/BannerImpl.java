package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.BannerForm;
import com.ecommerce.app.model.entity.Banner;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.repository.BannerRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.BannerService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BannerImpl implements BannerService {
    private final BannerRepository bannerRepository;;

    private final ProductRepository productRepository;

    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Optional<Banner> findById(String id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BANNER_NOT_FOUND));

        return Optional.of(banner);
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
        Banner banner = bannerRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BANNER_NOT_FOUND));
        if (banner.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.BANNER_CANNOT_DELETE);
        }

        banner.setStatus(Status.DELETED);
        bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(String id,BannerForm bannerForm) {
       Banner banner = bannerRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BANNER_NOT_FOUND));
       banner.setTitle(bannerForm.getTitle());
       banner.setDescription(bannerForm.getDescription());
       Banner savedBanner = bannerRepository.save(banner);
       return savedBanner;
    }


}
