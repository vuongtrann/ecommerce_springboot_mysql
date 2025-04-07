package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, String> {
}
