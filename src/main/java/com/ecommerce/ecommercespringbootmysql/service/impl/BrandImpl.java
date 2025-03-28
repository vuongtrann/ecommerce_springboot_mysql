package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.BrandProjection;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.CollectionProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import com.ecommerce.ecommercespringbootmysql.model.entity.Collection;
import com.ecommerce.ecommercespringbootmysql.repository.BrandRepository;
import com.ecommerce.ecommercespringbootmysql.service.BrandService;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return Optional.of(brandRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND)));
    }

    @Override
    public Page<BrandProjection> getAllBrands(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(sortBy, direction);
        if(direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else if(direction.equalsIgnoreCase("asc")) {
            sort = sort.ascending();
        }
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
        if (!brand.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.BRAND_CANNOT_DELETE);
        }
        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);
    }



}
