package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.model.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    CommentResponse  createComment(CommentForm form, Long uid, String productId);
    List<CommentResponse> getCommentsByUserId(Long uid);

    List<CommentResponse> getCommentsByProductId(String productId);

    Page<CommentResponse> getAllComments(int page, int size, String sortBy, String direction);
    void deleteComment(String commentId, Long userUid);
    void hideComment(String commentId, Long userUid);

    CommentResponse updateComment(String commentId, Long userUid, CommentForm form);
}
