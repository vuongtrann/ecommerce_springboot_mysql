package com.ecommerce.ecommercespringbootpostgre.service.impl;

import com.ecommerce.ecommercespringbootpostgre.exception.AppException;
import com.ecommerce.ecommercespringbootpostgre.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.BrandProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Brand;
import com.ecommerce.ecommercespringbootpostgre.repository.BrandRepository;
import com.ecommerce.ecommercespringbootpostgre.service.BrandService;
import com.ecommerce.ecommercespringbootpostgre.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootpostgre.utils.ErrorCode;
import com.ecommerce.ecommercespringbootpostgre.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandImpl implements BrandService {
    private final BrandRepository  brandRepository;
    SlugifyService slugify;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> findById(String id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));

        return Optional.of(brand);
    }

    @Override
    public Page<BrandProjection> getAllBrands(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return brandRepository.findAllBrandBy(pageable);
    }

    @Override
    public Brand createBrand(BrandForm brandForm) {

        Brand brand = new Brand(
                brandForm.getName(),
                brandForm.getDescription(),
                slugify.generateSlug(brandForm.getName())
        );
        Brand savedBrand = brandRepository.save(brand);
        return savedBrand;
    }

    public Brand updateBrand( String id,BrandForm brandForm) {
       Brand brand = brandRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
       brand.setName(brandForm.getName());
       brand.setDescription(brandForm.getDescription());
       brand.setSlug(slugify.generateSlug(brandForm.getName()));
       Brand savedBrand = brandRepository.save(brand);
       return savedBrand;
    }

    public void deleteBrand(String id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
        if (brand.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.BRAND_CANNOT_DELETE);
        }
        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);
    }

    @Override
    public void changeStatus(String id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        if (brand.getStatus() == Status.ACTIVE) {
            brand.setStatus(Status.INACTIVE);
        } else {
            brand.setStatus(Status.ACTIVE);
        }
        brandRepository.save(brand);
    }


}
