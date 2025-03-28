package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BrandForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.BrandProjection;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.CollectionProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand save(Brand brand);
    Optional<Brand> findById(String id);
    Page<BrandProjection> getAllBrands(int page, int size, String sortBy, String direction);
    Brand createBrand(BrandForm brandForm);
    Brand updateBrand(String id ,BrandForm brandForm);
    void deleteBrand(String id);

}
