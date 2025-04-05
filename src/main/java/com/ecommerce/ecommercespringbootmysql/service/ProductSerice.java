package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.ProductForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.ProductProjection;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.projection.TagProjection;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.ecommerce.ecommercespringbootmysql.model.entity.Variant.VariantType;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductSerice {
    Page<ProductProjection> findAll(int page, int size, String sortBy, String direction);
    Product save(Product product);
    Product create(ProductForm form);
    Product update(String id, ProductForm form);
    Product uploadImage(String id, List<MultipartFile> files);
    void delete(String id);
    Optional<Product> findById(String id);
    Optional<Product> findBySlug(String slug);
    void changeStatus(String id);


    /**Variant Type*/
    List<VariantType> getVariantTypes();
    Optional<VariantType> getVariantType(Long id);
    VariantType createVariantType(VariantType variantType);
    VariantType updateVariantType(String id, VariantType variantType);

}
