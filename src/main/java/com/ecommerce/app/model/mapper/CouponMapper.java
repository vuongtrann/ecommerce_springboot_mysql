package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.request.CouponForm;
import com.ecommerce.app.model.dao.response.dto.CouponResponse;
import com.ecommerce.app.model.dao.response.dto.CouponTargetInfo;
import com.ecommerce.app.model.entity.Coupon;
import com.ecommerce.app.utils.Enum.CouponStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponMapper {
    public Coupon toEntity(CouponForm request) {
        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setTotalUsageLimit(request.getTotalUsageLimit());
        coupon.setCurrentUsageCount(0);
        coupon.setStatus(CouponStatus.ACTIVE);
        coupon.setCreatedAt(System.currentTimeMillis());
        coupon.setUpdatedAt(System.currentTimeMillis());
        coupon.setCreatedBy(request.getCreatedBy());
        coupon.setUpdatedBy(request.getCreatedBy());
        return coupon;
    }

    public CouponResponse toResponse(Coupon coupon) {
        CouponResponse res = new CouponResponse();
        res.setId(coupon.getId());
        res.setCode(coupon.getCode());
        res.setDiscountType(coupon.getDiscountType());
        res.setDiscountValue(coupon.getDiscountValue());
        res.setStartDate(coupon.getStartDate());
        res.setEndDate(coupon.getEndDate());
        res.setTotalUsageLimit(coupon.getTotalUsageLimit());
        res.setCurrentUsageCount(coupon.getCurrentUsageCount());
        res.setStatus(coupon.getStatus());

        List<CouponTargetInfo> targets = coupon.getCouponTargets().stream()
                .map(t -> {
                    CouponTargetInfo info = new CouponTargetInfo();
                    info.setType(t.getTargetType());
                    info.setTargetId(t.getTargetId());
                    return info;
                }).toList();

        res.setTargets(targets);
        return res;
    }
}
