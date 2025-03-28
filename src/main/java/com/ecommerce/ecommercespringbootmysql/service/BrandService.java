package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand save(Brand brand);
    Brand findById(String id);
    List<Brand> getAllBrands();
    Brand createBrand(BrandForm brandForm);
    Brand updateBrand(String id ,BrandForm brandForm);
    void deleteBrand(String id);

}
