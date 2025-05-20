package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.utils.Enum.CouponTargetType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTargetInfo {
    private CouponTargetType type;
    private String targetId;
}