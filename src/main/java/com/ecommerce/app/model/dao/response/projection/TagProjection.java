package com.ecommerce.app.model.dao.response.projection;

import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.utils.Status;

import java.util.List;

public interface TagProjection {
    String getId();
    String getTagName();
    List<Product> getProducts();
    Status getStatus();
    Long getCreatedAt();
    Long getUpdatedAt();
    String getCreatedBy();
    String getUpdatedBy();

}
