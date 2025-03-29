package com.ecommerce.ecommercespringbootmysql.model.dao.response.projection;

public interface CollectionProjection {
    String getId();
    String getCollectionName();
    String getCollectionDescription();
    String getCollectionImage();
    String getSlug();
}
