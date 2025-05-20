package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.utils.Enum.CouponStatus;
import com.ecommerce.app.utils.Enum.CouponTargetType;
import com.ecommerce.app.utils.Enum.DiscountType;
import lombok.Data;

import java.util.List;

@Data
public class CouponResponse {
    private String id;
    private String code;
    private DiscountType discountType;
    private Double discountValue;

    private Long startDate;
    private Long endDate;

    private Integer totalUsageLimit;
    private Integer currentUsageCount;

    private CouponStatus status;

    private List<CouponTargetInfo> targets;
}

