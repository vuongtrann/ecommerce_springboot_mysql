package com.ecommerce.ecommercespringbootpostgre.service.impl;

import com.ecommerce.ecommercespringbootpostgre.exception.AppException;
import com.ecommerce.ecommercespringbootpostgre.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Banner;
import com.ecommerce.ecommercespringbootpostgre.repository.BannerRepository;
import com.ecommerce.ecommercespringbootpostgre.service.BannerService;
import com.ecommerce.ecommercespringbootpostgre.utils.ErrorCode;
import com.ecommerce.ecommercespringbootpostgre.utils.Status;
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
