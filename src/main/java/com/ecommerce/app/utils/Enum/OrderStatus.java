package com.ecommerce.app.utils.Enum;

public enum OrderStatus {
    PENDING,        // Đơn hàng mới tạo, đang chờ xử lý
    PROCESSING,     // Đơn hàng đang được xử lý
    SHIPPED,        // Đơn hàng đã được giao đi
    DELIVERED,      // Đơn hàng đã được giao đến khách hàng
    CANCELLED,      // Đơn hàng đã bị hủy
    RETURNED,       // Đơn hàng đã được trả lại
    REFUNDED        // Đơn hàng đã được hoàn tiền
}
