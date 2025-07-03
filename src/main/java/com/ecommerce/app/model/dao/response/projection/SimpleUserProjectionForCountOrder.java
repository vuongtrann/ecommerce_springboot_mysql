package com.ecommerce.app.model.dao.response.projection;

public interface SimpleUserProjectionForCountOrder {
    Long getUserUid();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getAvatar();
    String getPhone();
    Long getTotalOrder();
}
