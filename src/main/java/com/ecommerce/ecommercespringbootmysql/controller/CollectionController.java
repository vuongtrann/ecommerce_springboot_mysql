package com.ecommerce.ecommercespringbootmysql.controller;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.BannerForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.CollectionForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootmysql.model.entity.Banner;
import com.ecommerce.ecommercespringbootmysql.model.entity.Collection;
import com.ecommerce.ecommercespringbootmysql.service.CollectionService;
import com.ecommerce.ecommercespringbootmysql.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollectionController {
    /***TODO: Tiến dựa theo mấy cái controller trước làm cái này tương tự , làm xong hết xóa line này */
    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<AppResponse<List<Collection>>> getAllCollections() {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        collectionService.getAllCollections()
                )
        );
    }

    @PostMapping
    public ResponseEntity<AppResponse<Collection>> addCollection(@RequestBody CollectionForm collectionForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        collectionService.createCollection(collectionForm)
                )
        );
    }


    @DeleteMapping("/{collectionId}")
    public ResponseEntity<AppResponse<Collection>> deleteCollection(@PathVariable String collectionId) {
        Collection collection = collectionService.findById(collectionId);
        collectionService.deleteCollection(collectionId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        collection
                )
        );
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<AppResponse<Collection>> updateCollection(@PathVariable String collectionId, @RequestBody CollectionForm collectionForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        collectionService.updateCollection(collectionId,collectionForm)
                )
        );
    }

}
