package com.ecommerce.app.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.app.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_FILE_COUNT = 10;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");



    @Override
    public List<String> uploadImages(List<MultipartFile> files, String folderName) {
        validateFiles(files);
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(files.size(), 5));
        List<Future<String>> futures = new ArrayList<>();

        for (MultipartFile file : files) {
            futures.add(executor.submit(() -> uploadSingleImage(file, folderName)));
        }

        List<String> uploadedUrls = futures.stream().map(f -> {
            try {
                return f.get();
            } catch (Exception e) {

                throw new RuntimeException("Upload failed", e);
            }
        }).collect(Collectors.toList());

        executor.shutdown();
        return uploadedUrls;

//        return  files.parallelStream()
//                .map(file -> {
//                    try {
//                        return uploadSingleImage(file, folderName);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Upload failed", e);
//                    }
//                })
//                .collect(Collectors.toList());
    }

    private String uploadSingleImage(MultipartFile file, String folderName) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + Instant.now().toEpochMilli();
//        String fileName = "" + Instant.now().toEpochMilli();
        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folderName,
                "public_id", fileName,
                "overwrite", true,
                "resource_type", "auto"
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("secure_url").toString();
    }

    private void validateFiles(List<MultipartFile> files) {
        if (files.size() > MAX_FILE_COUNT) {
            throw new IllegalArgumentException("Chỉ được upload tối đa 10 ảnh một lần.");
        }

        for (MultipartFile file : files) {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("File quá lớn (>5MB): " + file.getOriginalFilename());
            }

            String ext = getFileExtension(file.getOriginalFilename());
            if (!ALLOWED_EXTENSIONS.contains(ext.toLowerCase())) {
                throw new IllegalArgumentException("File định dạng không hỗ trợ: " + ext);
            }
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return "";
    }


    @Override
    public void deleteImageByUrl(String imageUrl) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xóa ảnh Cloudinary: " + e.getMessage());
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        try {
            String[] parts = imageUrl.split("/");
            StringBuilder publicId = new StringBuilder();
            for (int i = 7; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    publicId.append(parts[i], 0, parts[i].lastIndexOf("."));
                } else {
                    publicId.append(parts[i]).append("/");
                }
            }
            return publicId.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("URL không hợp lệ: " + imageUrl);
        }
    }


    @Value("${cloudinary.folder.avatar}")
    private String avatarFolder;

    @Override
    public String uploadAvatar(MultipartFile file, Long userId) {
        try {
            String folder = "ecommerce/users/" + userId;
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "folder", folder,
                    "public_id", "avatar"
            ));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Upload avatar failed", e);
        }
    }

}
