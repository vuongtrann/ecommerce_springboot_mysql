package com.ecommerce.app.model.dao.response.projection;

import com.ecommerce.app.model.entity.Image;
import com.ecommerce.app.utils.Enum.Status;

import java.util.List;

public interface ProductProjection {
     String getId();
     String getName();
     String getDescription();
     String getSlug();
     String getPrimaryImageURL();
//     List<Category> getCategories();
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
