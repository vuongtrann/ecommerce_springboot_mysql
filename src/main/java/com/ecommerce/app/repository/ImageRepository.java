package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {

}
