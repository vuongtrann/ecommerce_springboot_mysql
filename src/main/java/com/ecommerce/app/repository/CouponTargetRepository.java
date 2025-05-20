package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.CouponTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponTargetRepository extends JpaRepository<CouponTarget, String> {
}
