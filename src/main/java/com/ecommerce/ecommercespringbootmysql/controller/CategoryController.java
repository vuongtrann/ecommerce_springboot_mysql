package com.ecommerce.ecommercespringbootmysql.controller;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.CategoryForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootmysql.model.entity.Category;
import com.ecommerce.ecommercespringbootmysql.service.CategoryService;
import com.ecommerce.ecommercespringbootmysql.utils.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<AppResponse<Category>> create(@RequestBody CategoryForm form) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.CREATED,
                        categoryService.create(form)
                )
        );
    }

    @GetMapping()
    public ResponseEntity<AppResponse<List<Category>>> findAll() {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<Category>> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findById(id).orElse(null)
                )
        );
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<AppResponse<Category>> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findBySlug(slug).orElse(null)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Category>> update(@PathVariable String id, @RequestBody CategoryForm form) {
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        categoryService.update(id, form)
                )
        );
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<AppResponse<Category>> setActivate(@PathVariable String id, @RequestParam boolean isActive) {
        categoryService.setActivate(id, isActive);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        categoryService.findById(id).orElse(null)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse<String>> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.DELETED,
                        "Deleted successfully !"
                )
        );
    }
}
