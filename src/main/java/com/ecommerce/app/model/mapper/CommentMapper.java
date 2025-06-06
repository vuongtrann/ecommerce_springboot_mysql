package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.model.entity.Comment;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.utils.Status;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static Comment toEntity(CommentForm form, User user, Product product) {
        return Comment.builder()
                .content(form.getContent() != null ? form.getContent().trim() : null)
                .user(user)
                .product(product)
                .build();
    }


    public static List<CommentResponse> toResponseList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toUserInfoResponse)
                .collect(Collectors.toList());
    }

    public static Page<CommentResponse> toResponsePage(Page<Comment> comments) {
        return comments.map(CommentMapper::toUserInfoResponse);
    }


    public static CommentResponse toUserInfoResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .userUid(comment.getUser().getUID() != null ? comment.getUser().getUID() : null)
                .content(comment.getContent())
                .firstName(comment.getUser().getFirstName())
                .lastName(comment.getUser().getLastName())
                .avatar(comment.getUser().getAvatar())
                .email(comment.getUser().getEmail())
                .status(comment.getStatus())
                .build();
    }


}
