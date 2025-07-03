package com.ecommerce.app.model.dao.response.projection;

public interface OrderStatisticProjection {
    Long getUserUid();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getAvatar();
    Long getTotalOrder();
}
