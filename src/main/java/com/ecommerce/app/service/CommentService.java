package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.model.entity.Comment;

import java.util.List;

public interface CommentService {

    void createComment(CommentForm form, Long userId, String productId);
    List<CommentResponse> getCommentsByUserId(Long userId);
}
