package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.model.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.product.id = :productId")
    void deleteByProductId(@Param("productId") String productId);



    Optional<Image> findByUrl(String url);




}
