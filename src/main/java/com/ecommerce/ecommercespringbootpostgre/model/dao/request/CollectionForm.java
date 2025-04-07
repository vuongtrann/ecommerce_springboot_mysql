package com.ecommerce.ecommercespringbootpostgre.model.dao.request;

import lombok.Data;

@Data
public class CollectionForm {


    private String collectionName;
    private String collectionDescription;
    private String collectionImage;
    private String collectionSlug;
}
