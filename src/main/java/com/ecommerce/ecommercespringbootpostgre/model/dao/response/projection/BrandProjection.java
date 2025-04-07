package com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection;

import com.ecommerce.ecommercespringbootpostgre.utils.Status;

public interface BrandProjection {
    String getId();
    String getName();
    String getSlug();
    String getDescription();
    Status getStatus();
}
