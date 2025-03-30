package com.ecommerce.ecommercespringbootmysql.model.dao.request;

import lombok.Data;

@Data
public class BrandForm {

    private String name;
    private String slug;
    private String description;
}
