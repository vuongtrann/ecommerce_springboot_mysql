package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import com.ecommerce.ecommercespringbootmysql.repository.BrandRepository;
import com.ecommerce.ecommercespringbootmysql.service.BrandService;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Brand findById(String id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
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
       Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException(" not found"));
       brand.setName(brandForm.getName());
       brand.setDescription(brandForm.getDescription());
       brand.setSlug(slugify.generateSlug(brandForm.getName()));
       Brand savedBrand = brandRepository.save(brand);
       return savedBrand;
    }

    public void deleteBrand(String id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        brandRepository.delete(brand);
    }



}
