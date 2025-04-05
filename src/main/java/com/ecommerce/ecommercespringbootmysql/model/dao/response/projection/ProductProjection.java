package com.ecommerce.ecommercespringbootmysql.model.dao.response.projection;

import com.ecommerce.ecommercespringbootmysql.model.entity.Image;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;

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
