package com.ecommerce.app.model.dao.response.projection;


public interface ProductWithAvgRatingProjection {
    String getProductId();
    String getProductName();
    String getProductDescription();
    String getPrimaryImageUrl();
    Double getSellingPrice();
    Double getAvgRating();
}
