package com.ecommerce.ecommercespringbootmysql.model.dao.response.projection;

import com.ecommerce.ecommercespringbootmysql.utils.Status;

public interface BrandProjection {
    String getId();
    String getName();
    String getSlug();
    String getDescription();
    Status getStatus();
}
