package com.ecommerce.app.model.dao.response.projection;

import com.ecommerce.app.utils.Enum.Status;

public interface CategoryWithTotalProductProjection {
    String getId();
    String getName();
    Integer getStatus();
    Integer getTotalProduct();
}
