package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Enum.CouponStatus;
import com.ecommerce.app.utils.Enum.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Coupon")
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String code; // Mã coupon

    // Thông tin giảm giá
    private DiscountType discountType;
    private Double discountValue;

    // Thời gian hiệu lực
    private Long startDate;
    private Long endDate;

    // Giới hạn sử dụng
    private Integer totalUsageLimit; // Tổng số lần sử dụng tối đa
    private Integer currentUsageCount = 0; // Số lần đã sử dụng

    // Trạng thái
    private CouponStatus status = CouponStatus.ACTIVE;

    private Long createdAt;
    private Long updatedAt;
    private Long createdBy;
    private Long updatedBy;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CouponTarget> couponTargets = new ArrayList<>();
}
