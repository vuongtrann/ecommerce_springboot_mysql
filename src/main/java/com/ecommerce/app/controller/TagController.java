package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.TagForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.model.entity.Tag;
import com.ecommerce.app.service.TagService;
import com.ecommerce.app.utils.Enum.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @GetMapping
    public ResponseEntity<Page<TagProjection>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(tagService.findAll(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<Optional<Tag>>> getTagById(@PathVariable String id) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        tagService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<AppResponse<Tag>> addTag(@RequestBody TagForm tagForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        tagService.create(tagForm)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Tag>> updateTag(@PathVariable String id, @RequestBody TagForm tagForm) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        tagService.update(id, tagForm)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse<String>> deleteTag(@PathVariable String id) {
        tagService.delete(id);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        id
                )
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppResponse<String>> changeStatus(@PathVariable String id) {
        tagService.changeStatus(id);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        id
                )
        );
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<AppResponse<String>> addTagToProduct(
            @PathVariable String productId,
            @RequestBody Map<String, String> body) {

        String tagId = body.get("id");
        tagService.addTagToProduct(productId, tagId);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.ADD_TAG_PRODUCT,
                        "Check productId: " + productId
                )
        );
    }
}
