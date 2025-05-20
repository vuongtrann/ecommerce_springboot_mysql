package com.ecommerce.app.model.entity;

import com.ecommerce.app.utils.Enum.CouponTargetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "CouponTarget")
@Table(name = "coupon_targets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTarget {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private CouponTargetType targetType; // Loại đối tượng (USER, PRODUCT, GLOBAL)
    private String targetId; // ID của đối tượng (userId, productId, hoặc null cho GLOBAL)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon; // Mối quan hệ với Coupon
}
