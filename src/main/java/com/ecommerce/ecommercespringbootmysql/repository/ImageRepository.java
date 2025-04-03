package com.ecommerce.ecommercespringbootmysql.repository;

import com.ecommerce.ecommercespringbootmysql.model.entity.Image;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.ecommerce.ecommercespringbootmysql.model.entity.Variant.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {

}
