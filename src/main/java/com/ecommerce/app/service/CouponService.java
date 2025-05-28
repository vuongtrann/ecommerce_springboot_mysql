package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CouponForm;
import com.ecommerce.app.model.dao.response.dto.CouponResponse;
import com.ecommerce.app.utils.Enum.CouponTargetType;

import java.util.List;

public interface CouponService {
    CouponResponse createCoupon(CouponForm form);
    boolean canApplyCouponOr(String couponCode, Long userId, Long productId) ;
    boolean canApplyCouponAnd(String couponCode, Long userId, Long productId);
    void applyCoupon(String couponCode);
    void cancelCoupon(String couponId, Long updatedBy);
    CouponResponse updateCoupon(String couponId, CouponForm form);
    void addTargets(String couponId, CouponTargetType type, List<Long> targetIds) ;
    List<CouponResponse> getValidCouponsForUser(Long userId, Long productId);


    }
