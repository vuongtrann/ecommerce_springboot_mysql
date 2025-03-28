package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.ProductForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductSerice {
    Product save(Product product);
    Product create(ProductForm form);
    Product update(String id, ProductForm form);
    Product uploadImage(String id, List<MultipartFile> files);
    void delete(String id);
    Optional<Product> findById(String id);
    Optional<Product> findBySlug(String slug);



}
