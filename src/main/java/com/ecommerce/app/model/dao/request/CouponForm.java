package com.ecommerce.app.model.dao.request;

import com.ecommerce.app.utils.Enum.CouponTargetType;
import com.ecommerce.app.utils.Enum.DiscountType;
import lombok.Data;

import java.util.List;

@Data
public class CouponForm {
    private String code;
    private DiscountType discountType;
    private Double discountValue;

    private Long startDate;
    private Long endDate;

    private Integer totalUsageLimit;

    private CouponTargetType applyType;
    private List<String> targetIds;

    private  Long createdBy;
    private  Long updatedBy;
}
