package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.BrandForm;
import com.ecommerce.app.model.dao.response.projection.BrandProjection;
import com.ecommerce.app.model.entity.Brand;
import com.ecommerce.app.model.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand save(Brand brand);
    Brand findById(String id);
    Page<BrandProjection> getAllBrands(int page, int size, String sortBy, String direction);
    Brand createBrand(BrandForm brandForm);
    Brand updateBrand(String id ,BrandForm brandForm);
    void deleteBrand(String id);
    void changeStatus(String id);
    Brand findBySlug(String slug);
    List<Brand> findAllBrandsByList();

    List<Brand> findByIdIn(List<String> ids);
    void addBrandToProduct(String productId, String brandId);

}
