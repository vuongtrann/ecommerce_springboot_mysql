package com.ecommerce.app.service.impl;

import com.ecommerce.app.model.dao.request.CouponForm;
import com.ecommerce.app.model.dao.response.dto.CouponResponse;
import com.ecommerce.app.model.entity.Coupon;
import com.ecommerce.app.model.entity.CouponTarget;
import com.ecommerce.app.model.mapper.CouponMapper;
import com.ecommerce.app.repository.CouponRepository;
import com.ecommerce.app.repository.CouponTargetRepository;
import com.ecommerce.app.service.CouponService;
import com.ecommerce.app.utils.Enum.CouponTargetType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponServiceImpl implements CouponService {
    CouponRepository couponRepository;
    CouponMapper couponMapper;
     CouponTargetRepository couponTargetRepository;
    @Override
    public CouponResponse createCoupon(CouponForm form) {
        Coupon coupon = couponMapper.toEntity(form);

        if (form.getApplyType() == CouponTargetType.GLOBAL) {
            CouponTarget globalTarget = new CouponTarget();
            globalTarget.setTargetType(CouponTargetType.GLOBAL);
            globalTarget.setTargetId(null);
            globalTarget.setCoupon(coupon);
            coupon.getCouponTargets().add(globalTarget);
        } else if (form.getTargetIds() != null) {
            for (String targetId : form.getTargetIds()) {
                CouponTarget target = new CouponTarget();
                target.setTargetType(form.getApplyType());
                target.setTargetId(targetId);
                target.setCoupon(coupon);
                coupon.getCouponTargets().add(target);
            }
        }

        Coupon saved = couponRepository.save(coupon);
        CouponResponse response = couponMapper.toResponse(saved);
        return response;
    }

    @Override
    public boolean canApplyCouponOr(String couponCode, Long userId, Long productId) {
        return false;
    }

    @Override
    public boolean canApplyCouponAnd(String couponCode, Long userId, Long productId) {
        return false;
    }

    @Override
    public void applyCoupon(String couponCode) {

    }

    @Override
    public void cancelCoupon(String couponId, Long updatedBy) {

    }

    @Override
    public CouponResponse updateCoupon(String couponId, CouponForm form) {
        return null;
    }

    @Override
    public void addTargets(String couponId, CouponTargetType type, List<Long> targetIds) {

    }

    @Override
    public List<CouponResponse> getValidCouponsForUser(Long userId, Long productId) {
        return List.of();
    }

    // Helper kiểm tra thời gian hiệu lực coupon
    private boolean isWithinValidDate(Coupon coupon) {
        long now = System.currentTimeMillis();
        return now >= coupon.getStartDate() && now <= coupon.getEndDate();
    }
}
