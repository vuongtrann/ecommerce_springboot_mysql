package com.ecommerce.ecommercespringbootmysql.model.dao.response.projection;

import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.ecommerce.ecommercespringbootmysql.utils.Status;

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
