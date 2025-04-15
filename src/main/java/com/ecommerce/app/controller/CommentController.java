package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    public ResponseEntity<String> createComment(
            @RequestBody CommentForm form,
            @RequestParam("userId") Long userId,
            @RequestParam("productId") String productId) {
        log.debug("Nhận yêu cầu tạo comment từ userId = {}, productId = {}", userId, productId);
        commentService.createComment(form, userId, productId);
        return ResponseEntity.ok("Tạo comment thành công.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(@PathVariable Long userId) {
        List<CommentResponse> responses = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(responses);
    }


}
