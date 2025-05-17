package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Enum.CouponStatus;
import com.ecommerce.app.utils.Enum.DiscountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Coupon")
@Table(name = "coupon")
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
}
