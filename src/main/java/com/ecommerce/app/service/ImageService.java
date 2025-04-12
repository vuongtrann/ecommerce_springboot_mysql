package com.ecommerce.app.service;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageService {
    List<String> addImagesToProduct(String productId, List<String> urls);

}
