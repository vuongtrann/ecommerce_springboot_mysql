package com.ecommerce.ecommercespringbootpostgre.service;

import java.util.List;

public interface ImageService {
    List<String> addImagesToProduct(String productId, List<String> urls);
}
