package com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection;

import com.ecommerce.ecommercespringbootpostgre.model.entity.Product;
import com.ecommerce.ecommercespringbootpostgre.utils.Status;

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
