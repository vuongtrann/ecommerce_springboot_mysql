package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.CollectionForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.projection.CollectionProjection;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.service.CollectionService;
import com.ecommerce.app.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CollectionController {
    /***TODO: Tiến dựa theo mấy cái controller trước làm cái này tương tự , làm xong hết xóa line này */
    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<Page<CollectionProjection>> getAllCollections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(collectionService.getAllCollections( page, size, sortBy, direction));
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
    public ResponseEntity<AppResponse<String>> deleteCollection(@PathVariable String collectionId) {

        collectionService.deleteCollection(collectionId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        collectionId
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

    @PutMapping("/{collectionId}/status")
    public ResponseEntity<AppResponse<String>> changeStatus(@PathVariable String collectionId) {
        collectionService.changeStatus(collectionId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Changed status successfully !"
                )
        );
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<AppResponse<String>> addCollectionToProduct(
            @PathVariable String productId,
            @RequestBody Map<String, String> body) {

        String collectionId = body.get("id");
        collectionService.addCollectionToProduct(productId, collectionId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                SuccessCode.ADD_COLLECTION_PRODUCT,
                        "Check productId: " + productId
        ));
    }

}
