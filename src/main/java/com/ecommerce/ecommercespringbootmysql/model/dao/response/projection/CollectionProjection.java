package com.ecommerce.ecommercespringbootmysql.model.dao.response.projection;

import com.ecommerce.ecommercespringbootmysql.utils.Status;

public interface CollectionProjection {
    String getId();
    String getCollectionName();
    String getCollectionDescription();
    String getCollectionImage();
    String getSlug();
    Status getStatus();
    Long getCreatedAt();
    Long getUpdatedAt();
}
