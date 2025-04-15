package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestParam Long uid,
                                           @RequestParam String productId,
                                           @RequestBody CommentForm form) {
        commentService.createComment(form, uid, productId);
        return ResponseEntity.ok("Tạo comment thành công");
    }

    @GetMapping("/user/uid/{uid}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUid(@PathVariable Long uid) {
        List<CommentResponse> comments = commentService.getCommentsByUserId(uid);
        return ResponseEntity.ok(comments);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<CommentResponse> response = commentService.getAllComments(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/hide/{id}")
    public ResponseEntity<String> hideComment(
            @PathVariable("id") String commentId,
            @RequestParam("userUid") Long userUid
    ) {
        commentService.hideComment(commentId, userUid);
        return ResponseEntity.ok("Đã ẩn comment thành công.");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("id") String commentId,
            @RequestParam("userUid") Long userUid
    ) {
        commentService.deleteComment(commentId, userUid);
        return ResponseEntity.ok("Đã xoá comment thành công.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(
            @PathVariable("id") String commentId,
            @RequestParam("userUid") Long userUid,
            @RequestBody CommentForm form
    ) {
        commentService.updateComment(commentId, userUid, form);
        return ResponseEntity.ok("Cập nhật comment thành công.");
    }


}
