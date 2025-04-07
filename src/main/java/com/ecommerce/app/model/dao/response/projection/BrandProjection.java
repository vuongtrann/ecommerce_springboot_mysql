package com.ecommerce.app.model.dao.response.projection;

import com.ecommerce.app.utils.Status;

public interface BrandProjection {
    String getId();
    String getName();
    String getSlug();
    String getDescription();
    Status getStatus();
}
