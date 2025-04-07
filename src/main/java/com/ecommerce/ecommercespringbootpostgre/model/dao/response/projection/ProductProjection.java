package com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection;

import com.ecommerce.ecommercespringbootpostgre.model.entity.Image;
import com.ecommerce.ecommercespringbootpostgre.utils.Status;

import java.util.List;

public interface ProductProjection {
     String getId();
     String getName();
     String getDescription();
     String getSlug();
     String getPrimaryImageURL();
//
//     List<String> getImageURLs();
     List<Image> getImages();
     String getSku();
     int getQuantity();
     int getQuantityAvailable();
     int getSoldQuantity();
     double getOriginalPrice(); //gia goc
     double getSellingPrice(); //gia ban
     double getDiscountedPrice(); //gia giam
     int getNoOfView();
     String getSellingType();
     double getAvgRating();
     int getNoOfRating();
     Status getStatus();
}
