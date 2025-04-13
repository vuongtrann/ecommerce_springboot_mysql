package com.ecommerce.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {

    List<String> uploadImages(List<MultipartFile> files, String folderName);

    void deleteImageByUrl(String imageUrl);
}
